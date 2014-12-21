package com.angelis.tera.game.config;

import com.angelis.tera.common.config.Property;

public class SpawnConfig {
    @Property(key = "spawn.creature.respawn.time", defaultValue = "30")
    public static int SPAWN_CREATURE_RESPAWN_TIME;

    @Property(key = "spawn.gather.respawn.time", defaultValue = "30")
    public static int SPAWN_GATHER_RESPAWN_TIME;

    @Property(key = "spawn.campfire.despawn.time", defaultValue = "1200")
    public static int SPAWN_CAMPFIRE_DESPAWN_TIME;
}
