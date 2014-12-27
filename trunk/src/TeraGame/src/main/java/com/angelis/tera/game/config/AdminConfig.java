package com.angelis.tera.game.config;

import com.angelis.tera.common.config.Property;

public class AdminConfig {
    @Property(key = "admin.command.prefix", defaultValue = "$")
    public static String ADMIN_COMMAND_PREFIX;
}
