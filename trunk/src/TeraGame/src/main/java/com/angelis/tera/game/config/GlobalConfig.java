package com.angelis.tera.game.config;

import com.angelis.tera.common.config.Property;

public class GlobalConfig {
    @Property(key = "global.name.banned.words", defaultValue = "fuck,boobs,dick")
    public static String[] GLOBAL_NAME_BANNED_WORDS;
    
    @Property(key = "global.welcome.title", defaultValue = "Tera emulator by Angelis yeah !")
    public static String GLOBAL_WELCOME_TITLE;

    @Property(key = "global.welcome.content", defaultValue = "Welcome on our emulator :)")
    public static String GLOBAL_WELCOME_CONTENT;
    
    @Property(key="global.data.management.use.cache", defaultValue="true")
    public static boolean GLOBAL_DATA_MANAGEMENT_USE_CACHE;

    @Property(key="global.data.threadpool.pool.size", defaultValue="3")
    public static int GLOBAL_THREADPOOL_POOL_SIZE;

    @Property(key="global.kill.rate", defaultValue="1")
    public static int GLOBAL_KILL_RATE;
}
