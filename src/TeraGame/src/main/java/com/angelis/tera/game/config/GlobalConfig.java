package com.angelis.tera.game.config;

import com.angelis.tera.common.config.Property;

public class GlobalConfig {
    @Property(key = "global.name.banned.words", defaultValue = "fuck,boobs,dick")
    public static String[] GLOBAL_NAME_BANNED_WORDS;

    @Property(key="global.data.management.use.cache", defaultValue="true")
    public static boolean GLOBAL_DATA_MANAGEMENT_USE_CACHE;

    @Property(key="global.data.threadpool.pool.size", defaultValue="3")
    public static int GLOBAL_THREADPOOL_POOL_SIZE;
}
