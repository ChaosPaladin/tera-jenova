package com.angelis.tera.login.process.services;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.angelis.tera.common.network.config.ServerConfig;
import com.angelis.tera.common.network.http.ServerRunner;
import com.angelis.tera.common.services.AbstractService;
import com.angelis.tera.login.config.NetworkConfig;
import com.angelis.tera.login.network.TeraLoginServer;

public class NetworkService extends AbstractService {
    
    /** LOGGER */
    private static Logger log = Logger.getLogger(NetworkService.class.getName());
    
    private TeraLoginServer server;
    
    private NetworkService() {
    }
    
    @Override
    public void onInit() {
        try {
            server = new TeraLoginServer(new ServerConfig(NetworkConfig.LOGIN_BIND_ADDRESS, NetworkConfig.LOGIN_BIND_PORT));
        }
        catch (final IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        
        ServerRunner.executeInstance(server);
        
        log.info("NetworkService started");
    }

    @Override
    public void onDestroy() {
        log.info("NetworkService stopped");
    }
    
    /** SINGLETON */
    public static NetworkService getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        protected static final NetworkService instance = new NetworkService();
    }
}
