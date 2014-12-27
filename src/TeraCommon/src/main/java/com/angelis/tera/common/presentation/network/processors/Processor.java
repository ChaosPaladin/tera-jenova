package com.angelis.tera.common.presentation.network.processors;

import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.channels.spi.SelectorProvider;

import org.apache.log4j.Logger;

import com.angelis.tera.common.presentation.network.AbstractServer;

public abstract class Processor implements Runnable {
    
    /** LOGGER */
    private static Logger log = Logger.getLogger(Processor.class.getName());
    
    protected final AbstractServer abstractServer;
    protected final Selector selector;
    
    /** GATE */
    protected final Object gate = new Object();
    
    public Processor(AbstractServer abstractServer) {
        this.abstractServer = abstractServer;
        try {
            this.selector = SelectorProvider.provider().openSelector();
        } catch (IOException e) {
            throw new RuntimeException("Can't initialize processor !");
        }
    }

    public void run() {
        for (;;) {
            try {
                this.dispatch();
                synchronized(gate) {}
            }
            catch (Exception e) {
                log.error("Dispatcher error! " + e, e);
            }
        }

    }
    
    public final Selector selector() {
        return this.selector;
    }
    
    public final Object getGate() {
        return this.gate;
    }
    
    public abstract void dispatch();
}
