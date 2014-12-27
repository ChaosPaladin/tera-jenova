package com.angelis.tera.common.presentation.network.connection;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;

import org.apache.log4j.Logger;

import com.angelis.tera.common.lang.EmptyEventable;
import com.angelis.tera.common.lang.IsObservable;
import com.angelis.tera.common.lang.IsObserver;
import com.angelis.tera.common.presentation.network.connection.event.AuthenticateEvent;
import com.angelis.tera.common.presentation.network.connection.event.ConnectEvent;
import com.angelis.tera.common.presentation.network.connection.event.DisconnectEvent;
import com.angelis.tera.common.presentation.network.crypt.CryptSession;
import com.angelis.tera.common.presentation.network.crypt.CryptState;
import com.angelis.tera.common.presentation.network.processors.Processor;

public abstract class AbstractTeraConnection implements ConnectEvent, DisconnectEvent, AuthenticateEvent, IsObserver<EmptyEventable> {

    /** LOGGER */
    private static Logger log = Logger.getLogger(AbstractTeraConnection.class.getName());
    
    /** GATE */
    protected final Object gate = new Object();
    
    /** CONSTANTS */
    public final static int BUFFER_SIZE = 131072*8;
    
    protected final SelectableChannel selectableChannel;
    protected SelectionKey selectionKey;

    protected final ByteBuffer readBuffer;
    protected final ByteBuffer writeBuffer;
    
    protected ConnectionState state = ConnectionState.NONE;
    protected CryptSession cryptSession;
    
    public AbstractTeraConnection(SelectableChannel socketChannel, SelectionKey selectionKey) {
        this.selectableChannel = socketChannel;
        
        // Read Buffer
        this.readBuffer = ByteBuffer.allocate(BUFFER_SIZE);
        this.readBuffer.order(ByteOrder.LITTLE_ENDIAN);
        
        // Write buffer
        this.writeBuffer = ByteBuffer.allocate(BUFFER_SIZE);
        this.writeBuffer.flip();
        this.writeBuffer.order(ByteOrder.LITTLE_ENDIAN);
        
        this.cryptSession = new CryptSession();
        this.cryptSession.addObserver(this);
        this.selectionKey = selectionKey;
    }
    
    public final void attachReadWriteProcessor(Processor readProcessor) {
        synchronized(readProcessor.getGate()) {
            if (!this.selectableChannel.isOpen()) {
                throw new RuntimeException("Trying to manage a connection while channel is already closed");
            }
            
            try {
                Selector selector = readProcessor.selector();
                if (selector != null) {
                    selector.wakeup();
                    this.selectionKey = this.selectableChannel.register(selector, SelectionKey.OP_READ, this);
                }
            } catch (ClosedChannelException e) {
                log.info(e.getMessage());
            }
        }
    }
    
    public final SelectableChannel channel() {
        return this.selectableChannel;
    }
    
    public final SelectionKey key() {
        return this.selectionKey;
    }
    
    public final ByteBuffer readBuffer() {
        return this.readBuffer;
    }
    
    public final ByteBuffer writeBuffer() {
        return this.writeBuffer;
    }

    public void close() {
        try {
            this.selectableChannel.close();
            this.onDisconnect();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
    
    public final void enableWriteInterest() {
        if (selectionKey.isValid()) {
            selectionKey.interestOps(selectionKey.interestOps() | SelectionKey.OP_WRITE);
            selectionKey.selector().wakeup();
        }
    }
    
    public final ConnectionState getState() {
        return state;
    }
    
    public void setState(ConnectionState state) {
        this.state = state;
    }
    
    public boolean isPendingClose() {
        return false; // TODO
    }
    
    public CryptSession getCryptSession() {
        return this.cryptSession;
    }
    
    public void onObservableUpdate(EmptyEventable event, IsObservable<EmptyEventable> observable, Object... data) {
        if (observable instanceof CryptSession) {
            CryptState cryptState = (CryptState) data[0];
            switch (cryptState) {
                case CRYPTED:
                    this.onAuthenticate();
                break;
                
                default:
            }
        }
    }
    
    public abstract boolean readPacket(final ByteBuffer buffer);
    public abstract boolean writePacket(final ByteBuffer buffer);
    
    public abstract boolean hasPacketToRead();
    public abstract boolean hasPacketToWrite();
}
