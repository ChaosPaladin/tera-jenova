package com.angelis.tera.common.presentation.network.factory;

import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

import com.angelis.tera.common.presentation.network.connection.AbstractTeraConnection;

public abstract class AbstractConnectionFactory<T extends AbstractTeraConnection> {
    public abstract T create(SocketChannel socketChannel, SelectionKey selectionKey);
}
