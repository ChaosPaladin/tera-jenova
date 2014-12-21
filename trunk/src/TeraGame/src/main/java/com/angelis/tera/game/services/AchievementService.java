package com.angelis.tera.game.services;

import java.util.List;

import javolution.util.FastList;

import org.apache.log4j.Logger;

import com.angelis.tera.common.services.AbstractService;
import com.angelis.tera.game.process.model.player.Achievement;
import com.angelis.tera.game.process.model.player.Player;

public class AchievementService extends AbstractService {

    /** LOGGER */
    private static Logger log = Logger.getLogger(AchievementService.class.getName());

    private AchievementService() {
    }
    
    @Override
    public void onInit() {
        log.info("AchievementService started");
    }

    @Override
    public void onDestroy() {
        log.info("AchievementService stopped");
    }
    
    public void onPlayerCreate(final Player player) {
        final List<Achievement> achievements = new FastList<>();
        player.setAchievements(achievements);
    }
    
    /** SINGLETON */
    public static AchievementService getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        protected static final AchievementService instance = new AchievementService();
    }
}
