package com.angelis.tera.game.config;

import com.angelis.tera.common.config.Property;

/**
 * @author Angelis
 */
public final class NetworkConfig {
    @Property(key = "gameserver.network.bind.host", defaultValue = "*")
    public static String GAME_BIND_ADDRESS;

    @Property(key = "gameserver.network.bind.port", defaultValue = "7804")
    public static int GAME_BIND_PORT;
    
    @Property(key = "gameserver.network.readwriteprocessor.count", defaultValue = "3")
    public static int GAME_READ_WRITE_PROCESSOR_COUNT;
    
    @Property(key="gameserver.network.shop.url", defaultValue="")
    public static String SHOP_URL;
}
