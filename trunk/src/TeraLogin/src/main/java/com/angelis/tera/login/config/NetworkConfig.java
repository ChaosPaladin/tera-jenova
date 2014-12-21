package com.angelis.tera.login.config;

import com.angelis.tera.common.config.Property;

public class NetworkConfig {
    @Property(key = "loginserver.network.bind.host", defaultValue = "*")
    public static String LOGIN_BIND_ADDRESS;

    @Property(key = "loginserver.network.bind.port", defaultValue = "80")
    public static int LOGIN_BIND_PORT;
}
