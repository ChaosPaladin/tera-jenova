package com.angelis.tera.game.process.services;

import org.apache.log4j.Logger;

import com.angelis.tera.common.process.services.AbstractService;
import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.server.SM_CRAFT_RECIPE_LIST;
import com.angelis.tera.game.presentation.network.packet.server.SM_CRAFT_STATS;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.model.player.craft.CraftStats;
import com.angelis.tera.game.process.model.player.craft.enums.CraftTypeEnum;

public class CraftService extends AbstractService {

    /** LOGGER */
    private static Logger log = Logger.getLogger(CraftService.class.getName());

    private CraftService() {
    }
    
    @Override
    public void onInit() {
        log.info("CraftService started");
    }

    @Override
    public void onDestroy() {
        log.info("CraftService stopped");
    }

    public void onPlayerCreate(final Player player) {
        player.setCraftStats(new CraftStats());
    }
    
    public void onPlayerEnterWorld(final Player player) {
        final TeraGameConnection connection = player.getConnection();
        connection.sendPacket(new SM_CRAFT_STATS(player.getCraftStats()));
        connection.sendPacket(new SM_CRAFT_RECIPE_LIST(player));
    }
    
    public void learnCraft(final Player player, final CraftTypeEnum craftType, final Integer learnedLevels) {
        final CraftStats craftStats = player.getCraftStats();
        
        final int newLevel = craftStats.getCraftLevel(craftType)+learnedLevels;
        craftStats.setCraftLevel(craftType, newLevel);
        
        completeCraftChange(player, craftType, newLevel);
    }
    
    public void unlearnCraft(final Player player, final CraftTypeEnum craftType, final Integer unlearnedLevels) {
        final CraftStats craftStats = player.getCraftStats();
        
        final int newLevel = craftStats.getCraftLevel(craftType)-unlearnedLevels;
        craftStats.setCraftLevel(craftType, newLevel);
        
        completeCraftChange(player, craftType, newLevel);
    }
    
    private final void completeCraftChange(final Player player, final CraftTypeEnum craftType, final Integer newLevel) {
        final TeraGameConnection connection = player.getConnection();
        
        switch (craftType) {
            default:
                // TODO SYSTEM MESSAGE FOR "YOUR CRAFT HAS INCREASED"
        }
        
        connection.sendPacket(new SM_CRAFT_STATS(player.getCraftStats()));
    }
    
    /** SINGLETON */
    public static CraftService getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        protected static final CraftService instance = new CraftService();
    }
}
