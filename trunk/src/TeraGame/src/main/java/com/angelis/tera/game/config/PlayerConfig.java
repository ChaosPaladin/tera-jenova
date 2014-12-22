package com.angelis.tera.game.config;

import com.angelis.tera.common.config.Property;

public class PlayerConfig {
    
    @Property(key = "player.name.pattern", defaultValue = "[A-Za-z]*")
    public static String PLAYER_NAME_PATTERN;
    
    @Property(key = "player.quit.timeout", defaultValue = "10")
    public static int PLAYER_QUIT_TIMEOUT;
    
    @Property(key = "player.delete.timeout", defaultValue = "3600")
    public static int PLAYER_DELETE_TIMEOUT;

    @Property(key = "player.max.level", defaultValue = "60")
    public static int PLAYER_MAX_LEVEL;

    @Property(key = "player.auto.save.delay", defaultValue = "900")
    public static int PLAYER_AUTO_SAVE_DELAY;

    @Property(key = "player.gain.stamina.delay", defaultValue = "5")
    public static int PLAYER_GAIN_STAMINA_DELAY;
    
    @Property(key = "player.gain.gathercraftpoint.delay", defaultValue = "300")
    public static short PLAYER_GAIN_GATHERCRAFTPOINT_DELAY;
    
    @Property(key = "player.gain.gathercraftpoint.amount", defaultValue = "5")
    public static short PLAYER_GAIN_GATHERCRAFTPOINT_AMOUNT;
    
    @Property(key = "player.gathercraftpoint.max.amount", defaultValue = "4000")
    public static short PLAYER_GATHERCRAFTPOINT_MAX_AMOUNT;

    @Property(key = "player.level.min.global.chat", defaultValue = "19")
    public static int PLAYER_LEVEL_MIN_GLOBAL_CHAT;

    @Property(key = "player.rest.rate", defaultValue = "10")
    public static long PLAYER_REST_RATE;
    
    @Property(key = "player.reaper.require.player.min.level", defaultValue = "40")
    public static int PLAYER_REAPER_REQUIRE_PLAYER_MIN_LEVEL;
}
