package com.angelis.tera.game.process.services;

import org.apache.log4j.Logger;

import com.angelis.tera.common.services.AbstractService;
import com.angelis.tera.game.process.model.player.Player;

public class GuildService extends AbstractService {
    /** LOGGER */
    private static Logger log = Logger.getLogger(GuildService.class.getName());

    private GuildService() {
    }

    @Override
    public void onInit() {
        log.info("GuildService started");
    }

    @Override
    public void onDestroy() {
        log.info("GuildService stopped");
    }
    
    public void onPlayerLeave(final Player player) {
        // TODO
    }
    
    /** SINGLETON */
    public static GuildService getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        protected static final GuildService instance = new GuildService();
    }
}
