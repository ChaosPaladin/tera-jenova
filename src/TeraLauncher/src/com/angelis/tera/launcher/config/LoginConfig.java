package com.angelis.tera.launcher.config;

import com.angelis.tera.common.config.Property;

public class LoginConfig {
    @Property(key = "login.url", defaultValue = "127.0.0.1")
    public static String LOGIN_URL;
    
    @Property(key = "login.port", defaultValue = "80")
    public static int LOGIN_PORT;
}
