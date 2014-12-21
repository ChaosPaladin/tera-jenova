package com.angelis.tera.login.config;

import com.angelis.tera.common.config.Property;

public class AccountConfig {
    
    @Property(key = "loginserver.account.create.if.login.not.found", defaultValue = "true")
    public static boolean ACCOUNT_CREATE_IF_LOGIN_NOT_FOUND;
}
