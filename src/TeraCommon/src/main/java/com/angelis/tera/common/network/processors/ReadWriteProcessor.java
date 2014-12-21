package com.angelis.tera.common.network.processors;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.angelis.tera.common.network.AbstractServer;
import com.angelis.tera.common.network.connection.AbstractTeraConnection;
import com.angelis.tera.common.network.connection.ConnectionState;

public class ReadWriteProcessor extends Processor {

    /** LOGGER */
    private static Logger log = Logger.getLogger(ReadWriteProcessor.class.getName());

    public ReadWriteProcessor(final AbstractServer abstractServer) {
        super(abstractServer);
    }

    @Override
    public void dispatch() {
        try {
            final int selection = this.selector.select();
            if (selection == 0) {
                return;
            }

            final Iterator<SelectionKey> iterator = this.selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                final SelectionKey selectionKey = iterator.next();
                iterator.remove();

                if (!selectionKey.isValid()) {
                    log.error("Key is invalid");
                    continue;
                }

                final AbstractTeraConnection connection = (AbstractTeraConnection) selectionKey.attachment();
                if (connection == null) {
                    log.error("Connection is null");
                    continue;
                }

                switch (selectionKey.readyOps()) {
                    case SelectionKey.OP_READ:
                        this.doRead(selectionKey);
                    break;

                    case SelectionKey.OP_WRITE:
                        this.doWrite(selectionKey);
                    break;

                    case SelectionKey.OP_READ | SelectionKey.OP_WRITE:
                        this.doRead(selectionKey);
                        if (selectionKey.isValid()) {
                            this.doWrite(selectionKey);
                        }
                    break;
                }
            }
        }
        catch (final IOException e) {
            throw new RuntimeException("IOException in ReadProcessor");
        }
    }

    private final <C extends AbstractTeraConnection> void doRead(final SelectionKey selectionKey) {
        @SuppressWarnings("unchecked")
        final C connection = (C) selectionKey.attachment();
        final SocketChannel socketChannel = (SocketChannel) selectionKey.channel();

        final ByteBuffer buffer = connection.readBuffer();
        final ByteBuffer cryptedBuffer = ByteBuffer.allocate(buffer.capacity());
        cryptedBuffer.order(ByteOrder.LITTLE_ENDIAN);
        cryptedBuffer.clear();

        int readCount = 0;
        try {
            readCount = socketChannel.read(cryptedBuffer);
        }
        catch (final IOException e) {
            log.error(e.getMessage(), e);
            connection.close();
            return;
        }

        if (readCount == -1) {
            log.error("Closing connection due to reaching end of stream.");
            connection.close();
            return;
        }
        else if (readCount == 0) {
            return;
        }

        cryptedBuffer.flip();
        connection.getCryptSession().decrypt(cryptedBuffer);
        cryptedBuffer.rewind();

        buffer.put(cryptedBuffer);
        buffer.flip();

        while (buffer.remaining() > 2) {
            if (connection.getState() == ConnectionState.AUTHENTICATED) {
                if (buffer.remaining() < buffer.getShort(buffer.position())) {
                    break;
                }
            }

            if (!parse(connection, buffer)) {
                log.error("Failed to parse data");
                connection.close();
                return;
            }
        }

        if (buffer.hasRemaining()) {
            log.error("Read buffer has remaining");
            buffer.compact();
        }
        else {
            buffer.clear();
        }
    }

    private final <C extends AbstractTeraConnection> void doWrite(final SelectionKey selectionKey) {
        @SuppressWarnings("unchecked")
        final C connection = (C) selectionKey.attachment();
        final SocketChannel channel = (SocketChannel) selectionKey.channel();

        final ByteBuffer buffer = connection.writeBuffer();

        /** Write remaining bytes before */
        if (buffer.hasRemaining()) {
            try {
                channel.write(buffer);
            }
            catch (final IOException e) {
                connection.close();
                return;
            }
        }

        while (true) {
            if (!connection.hasPacketToWrite()) {
                break;
            }

            buffer.clear();
            final boolean writeSuccess = connection.writePacket(buffer);
            if (!writeSuccess) {
                log.error("Write wasn't succefull");
                buffer.limit(0);
                break;
            }

            int writeCount = 0;
            try {
                writeCount = channel.write(buffer);
            }
            catch (final IOException e) {
                log.error(e.getMessage(), e);
            }

            if (writeCount == 0) {
                log.info("Write " + writeCount);
                return;
            }

            if (buffer.hasRemaining()) {
                log.info("Write buffer has remaining");
                return;
            }
        }

        selectionKey.interestOps(selectionKey.interestOps() & ~SelectionKey.OP_WRITE);

        if (connection.isPendingClose()) {
            log.info("Closing connection");
            connection.close();
        }
    }

    private final boolean parse(final AbstractTeraConnection connection, final ByteBuffer buffer) {
        int size = 0;
        switch (connection.getState()) {
            case CONNECTED:
                size = 128;
            break;

            case AUTHENTICATED:
                size = buffer.getShort();
                if (size > 1) {
                    size -= 2;
                }
                else if (size < 0) {
                    size = buffer.remaining();
                }
            break;

            default:
                // should never happend
                throw new RuntimeException();
        }

        try {
            /** Copy packet to temp buffer for reading */
            final ByteBuffer tempBuffer = (ByteBuffer) buffer.slice().limit(size);
            tempBuffer.order(ByteOrder.LITTLE_ENDIAN);

            /** Update buffer position after this packet */
            buffer.position(buffer.position() + size);

            return connection.readPacket(tempBuffer);
        }
        catch (final IllegalArgumentException e) {
            log.warn("Error on parsing input from client - account: " + connection + " packet size: " + size + " real size:" + buffer.remaining(), e);
            return false;
        }
    }
}
