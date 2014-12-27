package com.angelis.tera.login.process.services;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.angelis.tera.common.process.services.AbstractService;
import com.angelis.tera.login.process.delegate.database.ServerDelegate;
import com.angelis.tera.login.process.dto.Server;
import com.angelis.tera.login.process.exceptions.MissingRequiredFieldException;

public class ServerService extends AbstractService {
    /** LOGGER */
    private static Logger log = Logger.getLogger(ServerService.class.getName());

    /** DELEGATES */
    private final ServerDelegate serverDelegate = new ServerDelegate();

    @Override
    public void onInit() {
        log.info("ServerService started");
    }

    @Override
    public void onDestroy() {
        log.info("ServerService stopped");
    }

    public List<Server> getAll() {
        return serverDelegate.findAll();
    }

    public void createServer(final String name, final String port, final String ip) throws MissingRequiredFieldException {
        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(port) || StringUtils.isEmpty(ip)) {
            throw new MissingRequiredFieldException(name, port, ip);
        }

        final Server server = new Server();
        server.setServerName(name);
        server.setPort(Integer.parseInt(port));
        server.setIp(ip);

        serverDelegate.create(server);
    }

    /** SINGLETON */
    public static ServerService getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        protected static final ServerService instance = new ServerService();
    }
}
