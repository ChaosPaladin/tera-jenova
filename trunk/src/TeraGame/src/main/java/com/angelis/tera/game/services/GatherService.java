package com.angelis.tera.game.services;

import org.apache.log4j.Logger;

import com.angelis.tera.common.services.AbstractService;
import com.angelis.tera.game.network.SystemMessages;
import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.server.SM_PLAYER_GATHER_STATS;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.model.player.gather.GatherStats;
import com.angelis.tera.game.process.model.player.gather.enums.GatherTypeEnum;

public class GatherService extends AbstractService {

    /** LOGGER */
    private static Logger log = Logger.getLogger(GatherService.class.getName());

    private GatherService() {
    }
    
    @Override
    public void onInit() {
        log.info("GatherService started");
    }

    @Override
    public void onDestroy() {
        log.info("GatherService stopped");
    }

    public void onPlayerCreate(final Player player) {
        player.setGatherStats(new GatherStats());
    }
    
    public void learnGather(final Player player, final GatherTypeEnum gatherType, final Integer learnedLevels) {
        final GatherStats gatherStats = player.getGatherStats();
        
        final int newLevel = gatherStats.getGatherLevel(gatherType)+learnedLevels;
        gatherStats.setGatherLevel(gatherType, newLevel);
        
        completeGatherLevelChange(player, gatherType, newLevel);
    }
    
    public void unlearnGather(final Player player, final GatherTypeEnum gatherType, final Integer unlearnedLevels) {
        final GatherStats gatherStats = player.getGatherStats();
        
        final int newLevel = gatherStats.getGatherLevel(gatherType)-unlearnedLevels;
        gatherStats.setGatherLevel(gatherType, newLevel);
        
        completeGatherLevelChange(player, gatherType, newLevel);
    }
    
    private final void completeGatherLevelChange(final Player player, final GatherTypeEnum gatherType, final int newLevel) {
        final TeraGameConnection connection = player.getConnection();
        
        switch (gatherType) {
            case ESSENCE:
                connection.sendPacket(SystemMessages.YOUR_ESSENCE_GATHERING_HAS_INCREASED_TO(String.valueOf(newLevel)));
            break;
            
            case PLANT:
                connection.sendPacket(SystemMessages.YOUR_PLANT_COLLECTING_HAS_INCREASED_TO(String.valueOf(newLevel)));
            break;
            
            case MINE:
                connection.sendPacket(SystemMessages.YOUR_MINING_HAS_INCREASED_TO(String.valueOf(newLevel)));
            break;
            
            case BUG:
            break;
        }
        
        connection.sendPacket(new SM_PLAYER_GATHER_STATS(player.getGatherStats()));
    }
    
    /** SINGLETON */
    public static GatherService getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        protected static final GatherService instance = new GatherService();
    }
}
