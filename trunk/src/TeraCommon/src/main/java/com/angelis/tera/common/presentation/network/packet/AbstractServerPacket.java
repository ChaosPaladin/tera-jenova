package com.angelis.tera.common.presentation.network.packet;

import java.nio.ByteBuffer;

import com.angelis.tera.common.presentation.network.connection.AbstractTeraConnection;
import com.angelis.tera.common.utils.PrintUtils;

public abstract class AbstractServerPacket<C extends AbstractTeraConnection> extends AbstractPacket {
    
    /**
     * Write int to buffer.
     * 
     * @param buf
     * @param value
     */
    protected final void writeD(ByteBuffer buffer, int value) {
        buffer.putInt(value);
    }

    /**
     * Write short to buffer.
     * 
     * @param buf
     * @param value
     */
    protected final void writeH(ByteBuffer buffer, int value) {
        buffer.putShort((short) value);
    }

    /**
     * Write byte to buffer.
     * 
     * @param buf
     * @param value
     */
    protected final void writeC(ByteBuffer buffer, int value) {
        buffer.put((byte) value);
    }
    
    protected final void writeC(ByteBuffer buffer, boolean value) {
        buffer.put(value ? (byte) 1 : (byte) 0);
    }

    /**
     * Write double to buffer.
     * 
     * @param buf
     * @param value
     */
    protected final void writeDF(ByteBuffer buffer, double value) {
        buffer.putDouble(value);
    }

    /**
     * Write float to buffer.
     * 
     * @param buf
     * @param value
     */
    protected final void writeF(ByteBuffer buffer, float value) {
        buffer.putFloat(value);
    }

    /**
     * Write long to buffer.
     * 
     * @param buf
     * @param value
     */
    protected final void writeQ(ByteBuffer buffer, long value) {
        buffer.putLong(value);
    }

    /**
     * Write String to buffer
     * 
     * @param buf
     * @param text
     */
    protected final void writeS(ByteBuffer buffer, String text) {
        if (text == null) {
            buffer.putChar('\000');
        }
        else {
            final int len = text.length();
            for (int i = 0; i < len; i++) {
                buffer.putChar(text.charAt(i));
            }
            buffer.putChar('\000');
        }
    }
    
    protected final void writeS(ByteBuffer buffer, char value) {
        buffer.putChar(value);
    }
    
    protected final void writeB(ByteBuffer buffer, byte[] bytes) {
        buffer.put(bytes);
    }
    
    protected final void writeB(ByteBuffer buffer, String bytes) {
        this.writeB(buffer, PrintUtils.hex2bytes(bytes));
    }

    public abstract void write(C connection, ByteBuffer byteBuffer);
    protected abstract void writeImpl(C connection, ByteBuffer byteBuffer);
    public abstract void setOpcode(Short opcode);
    public abstract boolean showInDebug();
}
