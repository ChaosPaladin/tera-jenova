package com.angelis.tera.common.network;

import java.io.IOException;

import com.angelis.tera.common.network.config.ServerConfig;

import fi.iki.elonen.NanoHTTPD;

public abstract class AbstractHttpServer extends NanoHTTPD {
    
    public AbstractHttpServer(final ServerConfig serverConfig) throws IOException {
        super(serverConfig.getPort());
    }
}
