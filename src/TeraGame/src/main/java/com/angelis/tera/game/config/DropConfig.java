package com.angelis.tera.game.config;

import com.angelis.tera.common.config.Property;

public class DropConfig {
    @Property(key = "drop.creature.rate", defaultValue = "1")
    public static int DROP_CREATURE_RATE;

    @Property(key = "drop.free.time", defaultValue = "900")
    public static int DROP_FREE_TIME;

    @Property(key = "drop.despawn.time", defaultValue = "1800")
    public static int DROP_DESPAWN_TIME;
}
