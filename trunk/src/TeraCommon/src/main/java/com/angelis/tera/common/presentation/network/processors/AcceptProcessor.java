package com.angelis.tera.common.presentation.network.processors;

import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.angelis.tera.common.presentation.network.AbstractServer;
import com.angelis.tera.common.presentation.network.connection.AbstractTeraConnection;
import com.angelis.tera.common.presentation.network.factory.AbstractConnectionFactory;

public class AcceptProcessor extends Processor {

    private static Logger log = Logger.getLogger(AcceptProcessor.class.getName());

    public AcceptProcessor(final AbstractServer abstractServer, final AbstractConnectionFactory<? extends AbstractTeraConnection> connectionFactory) {
        super(abstractServer);
        try {
            this.abstractServer.getServerSocketChannel().register(this.selector, SelectionKey.OP_ACCEPT, connectionFactory);
            this.selector.wakeup();
        }
        catch (final ClosedChannelException e) {
            throw new RuntimeException("Can't initialize AcceptProcessor");
        }
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
                    log.info("Key isn't valid for accept");
                    continue;
                }

                switch (selectionKey.readyOps()) {
                    case SelectionKey.OP_ACCEPT:
                        final ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
                        final SocketChannel socketChannel = serverSocketChannel.accept();
                        socketChannel.configureBlocking(false);

                        @SuppressWarnings("unchecked")
                        final
                        AbstractConnectionFactory<AbstractTeraConnection> factory = (AbstractConnectionFactory<AbstractTeraConnection>) selectionKey.attachment();

                        final AbstractTeraConnection connection = factory.create(socketChannel, selectionKey);
                        connection.attachReadWriteProcessor(this.abstractServer.getReadWriteProcessor());

                        connection.onConnect();
                    break;
                }
            }
        }
        catch (final IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
