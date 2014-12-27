package com.angelis.tera.game.process.services;

import org.apache.log4j.Logger;

import com.angelis.tera.common.services.AbstractService;
import com.angelis.tera.game.presentation.network.packet.server.guild.SM_GUILD_VERSUS_STATUS;
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
    
    public void onPlayerRequestGuildInfo(final Player player) {
        if (player.getGuild() == null) {
            return;
        }
        // TODO
    }
    
    public void onPlayerRequestGuildApplication(final Player player) {
        if (player.getGuild() == null) {
            return;
        }
        
        // TODO
    }
    
    public void onPlayerRequestVersusStatus(final Player player) {
        player.getConnection().sendPacket(new SM_GUILD_VERSUS_STATUS());
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
