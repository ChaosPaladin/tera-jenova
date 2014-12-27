package com.angelis.tera.game.presentation.network.factory;

import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

import com.angelis.tera.common.presentation.network.factory.AbstractConnectionFactory;
import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;

public class TeraGameAcceptFactory extends AbstractConnectionFactory<TeraGameConnection> {

    @Override
    public TeraGameConnection create(final SocketChannel socketChannel, final SelectionKey selectionKey) {
        return new TeraGameConnection(socketChannel, selectionKey);
    }
}
