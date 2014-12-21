package com.angelis.tera.common.network.packet;

import java.nio.ByteBuffer;

import org.apache.log4j.Logger;

import com.angelis.tera.common.network.connection.AbstractTeraConnection;
import com.angelis.tera.common.utils.PrintUtils;

public abstract class AbstractClientPacket<C extends AbstractTeraConnection> extends AbstractPacket implements Runnable {

    /** LOGGER */
    private static Logger log = Logger.getLogger(AbstractClientPacket.class.getName());

    private final ByteBuffer byteBuffer;
    private final C connection;

    public AbstractClientPacket(final ByteBuffer byteBuffer, final C connection) {
        this.byteBuffer = byteBuffer;
        this.connection = connection;
    }

    public final boolean read() {
        try {
            readImpl();

            if (this.byteBuffer.hasRemaining()) {
                log.debug("Packet " + this.getClass().getSimpleName() + " not fully readed ! Remaining bytes {" + this.byteBuffer.remaining() + "} (Packet length was " + this.byteBuffer.limit() + ")");
                log.info(PrintUtils.toHex(byteBuffer));
                return true;
            }

            return true;
        }
        catch (final Exception re) {
            log.error("Reading failed for packet " + this, re);
            return false;
        }
    }

    @Override
    public final void run() {
        this.runImpl();
    }

    /**
     * Read byte from this bytebuffer.
     * 
     * @return byte
     */
    protected final byte readC() {
        try {
            return byteBuffer.get();
        }
        catch (final Exception e) {
            log.error("Missing C for: " + this.getClass().getSimpleName());
        }
        return 0;
    }

    /**
     * Read short from this bytebuffer.
     * 
     * @return short
     */
    protected final short readH() {
        try {
            return byteBuffer.getShort();
        }
        catch (final Exception e) {
            log.error("Missing H for: " + this.getClass().getSimpleName());
        }
        return 0;
    }

    /**
     * Read int from this bytebuffer.
     * 
     * @return
     */
    protected final int readD() {
        try {
            return byteBuffer.getInt();
        }
        catch (final Exception e) {
            log.error("Missing D for: " + this.getClass().getSimpleName());
        }
        return 0;
    }

    /**
     * Read double from this bytebuffer.
     * 
     * @return float
     */
    protected final float readF() {
        try {
            return byteBuffer.getFloat();
        }
        catch (final Exception e) {
            log.error("Missing F for: " + this.getClass().getSimpleName());
        }
        return 0;
    }

    /**
     * Read double from this bytebuffer.
     * 
     * @return double
     */
    protected final double readDF() {
        try {
            return byteBuffer.getDouble();
        }
        catch (final Exception e) {
            log.error("Missing DF for: " + this.getClass().getSimpleName());
        }
        return 0;
    }

    /**
     * Read long from this bytebuffer.
     * 
     * @return long
     */
    protected final long readQ() {
        try {
            return byteBuffer.getLong();
        }
        catch (final Exception e) {
            log.error("Missing Q for: " + this.getClass().getSimpleName());
        }
        return 0;
    }

    /**
     * Read String from this bytebuffer.
     * 
     * @return String
     */
    protected final String readS() {
        final StringBuffer sb = new StringBuffer();
        try {
            char ch;
            while ((ch = byteBuffer.getChar()) != 0) {
                sb.append(ch);
            }
        }
        catch (final Exception e) {
            log.error("Missing S for: " + this.getClass().getSimpleName());
        }
        return sb.toString();
    }

    /**
     * Read n bytes from this bytebuffer.
     * 
     * @param length
     * @return byte[]
     */
    protected final byte[] readB(final int length) {
        final byte[] result = new byte[length];
        this.readB(result);
        return result;
    }

    protected void readB(final byte[] bytes) {
        try {
            byteBuffer.get(bytes);
        }
        catch (final Exception e) {
            log.error("Missing byte[] for: " + this.getClass().getSimpleName());
        }
    }

    protected final byte[] readAll() {
        return this.readB(this.byteBuffer.remaining());
    }

    protected final void readPacketCompletely() {
        while (this.hasRemaining()) {
            readB(1);
        }
    }
    
    protected void readUntil(final short position) {
        while (this.byteBuffer.position() < position) {
            this.readC();
        }
    }

    public C getConnection() {
        return this.connection;
    }

    public boolean hasRemaining() {
        return this.byteBuffer.hasRemaining();
    }
    
    public int position() {
        return this.byteBuffer.position();
    }
    
    public void position(final int position) {
        this.byteBuffer.position(position);
    }

    /** This method is called to read packet content */
    protected abstract void readImpl();

    /**
     * This method is called to execute action after read (such as responding or
     * updating player ...)
     */
    protected abstract void runImpl();
}
