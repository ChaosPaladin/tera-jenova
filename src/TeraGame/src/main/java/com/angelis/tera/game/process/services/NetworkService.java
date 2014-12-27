package com.angelis.tera.game.process.services;

import org.apache.log4j.Logger;

import com.angelis.tera.common.presentation.network.config.GameServerConfig;
import com.angelis.tera.common.process.services.AbstractService;
import com.angelis.tera.game.config.NetworkConfig;
import com.angelis.tera.game.presentation.network.TeraGameServer;
import com.angelis.tera.game.presentation.network.factory.TeraGameAcceptFactory;
import com.angelis.tera.game.presentation.network.packet.ClientPacketHandler;
import com.angelis.tera.game.presentation.network.packet.ServerPacketHandler;

public class NetworkService extends AbstractService {

    /** LOGGER */
    private static Logger log = Logger.getLogger(DatabaseService.class.getName());

    private TeraGameServer teraServer;

    private NetworkService() {
    }

    @Override
    public void onInit() {
        teraServer = new TeraGameServer(new GameServerConfig(NetworkConfig.GAME_BIND_ADDRESS, NetworkConfig.GAME_BIND_PORT, new TeraGameAcceptFactory(), NetworkConfig.GAME_READ_WRITE_PROCESSOR_COUNT));
        new Thread(teraServer).start();

        ServerPacketHandler.init();
        ClientPacketHandler.init();
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
