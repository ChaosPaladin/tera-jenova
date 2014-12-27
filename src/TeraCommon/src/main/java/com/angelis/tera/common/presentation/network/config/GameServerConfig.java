package com.angelis.tera.common.presentation.network.config;

import com.angelis.tera.common.presentation.network.connection.AbstractTeraConnection;
import com.angelis.tera.common.presentation.network.factory.AbstractConnectionFactory;

public final class GameServerConfig extends ServerConfig {
    
    private final AbstractConnectionFactory<? extends AbstractTeraConnection> connectionFactory;
    private final int readWriteProcessorCount;
    
    public GameServerConfig(final String address, final int port, final AbstractConnectionFactory<? extends AbstractTeraConnection> connectionFactory, final int readWriteProcessorCount) {
        super(address, port);
        this.connectionFactory = connectionFactory;
        this.readWriteProcessorCount = readWriteProcessorCount;
    }

    public AbstractConnectionFactory<? extends AbstractTeraConnection> getConnectionFactory() {
        return connectionFactory;
    }

    public int getReadWriteProcessorCount() {
        return readWriteProcessorCount;
    }
}
