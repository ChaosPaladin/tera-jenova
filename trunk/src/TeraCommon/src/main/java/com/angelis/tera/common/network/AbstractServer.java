package com.angelis.tera.common.network;

import java.io.IOException;
import java.nio.channels.ServerSocketChannel;
import java.util.List;

import javolution.util.FastList;

import org.apache.log4j.Logger;

import com.angelis.tera.common.network.config.GameServerConfig;
import com.angelis.tera.common.network.processors.AcceptProcessor;
import com.angelis.tera.common.network.processors.Processor;
import com.angelis.tera.common.network.processors.ReadWriteProcessor;

public abstract class AbstractServer implements Runnable {

    /** LOGGER */
    private static Logger log = Logger.getLogger(AbstractServer.class.getName());

    private final List<Processor> acceptProcessors = new FastList<Processor>();
    private final List<Processor> readWriteProcessors = new FastList<Processor>();

    protected ServerSocketChannel serverSocket;
    private int currentReadWriteProcessor = 0;

    public AbstractServer(final GameServerConfig serverConfig) {
        try {
            this.serverSocket = ServerSocketChannel.open();
            this.serverSocket.configureBlocking(false);
            this.serverSocket.socket().bind(serverConfig.getInetSocketAddress());

            this.initAcceptProcessors(serverConfig);
            this.initReadWriteProcessors(serverConfig);
        }
        catch (final IOException e) {
            throw new RuntimeException("Can't init AbstractServer");
        }
    }

    @Override
    public final void run() {
        log.info("Server is now running with " + this.acceptProcessors.size() + " accept processor(s) and " + this.readWriteProcessors.size() + " read/write processor(s)");

        for (final Processor processor : this.acceptProcessors) {
            new Thread(processor).start();
        }

        for (final Processor processor : this.readWriteProcessors) {
            new Thread(processor).start();
        }
    }

    public final void stop() {

    }

    private final void initAcceptProcessors(final GameServerConfig serverConfig) {
        this.acceptProcessors.add(new AcceptProcessor(this, serverConfig.getConnectionFactory()));
    }

    private final void initReadWriteProcessors(final GameServerConfig serverConfig) {
        for (int i = 0; i < serverConfig.getReadWriteProcessorCount(); i++) {
            this.readWriteProcessors.add(new ReadWriteProcessor(this));
        }
    }

    public final Processor getAcceptProcessor() {
        return this.acceptProcessors.get(0); // TODO maybe multi
    }

    public final Processor getReadWriteProcessor() {
        if (++this.currentReadWriteProcessor >= this.readWriteProcessors.size()) {
            this.currentReadWriteProcessor = 0;
        }

        return this.readWriteProcessors.get(this.currentReadWriteProcessor);
    }

    public final ServerSocketChannel getServerSocketChannel() {
        return this.serverSocket;
    }
}
