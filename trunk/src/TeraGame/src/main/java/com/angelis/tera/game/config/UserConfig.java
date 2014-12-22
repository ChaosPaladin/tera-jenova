package com.angelis.tera.game.config;

import com.angelis.tera.common.config.Property;

public class UserConfig {
    @Property(key = "user.command.prefix", defaultValue = "!")
    public static String USER_COMMAND_PREFIX;
}
