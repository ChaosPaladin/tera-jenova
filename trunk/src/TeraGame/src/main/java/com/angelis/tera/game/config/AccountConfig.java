package com.angelis.tera.game.config;

import java.util.Locale;

import com.angelis.tera.common.config.Property;

public class AccountConfig {
    
    @Property(key = "account.autocreate", defaultValue = "true")
    public static boolean ACCOUNT_AUTOCREATE;
    
    @Property(key = "account.default.locale", defaultValue = "en")
    public static Locale ACCOUNT_DEFAULT_LOCALE;
}
