package com.angelis.tera.game.process.command;

public enum UserErrorMessageEnum {
    NOT_ALLOWED_ACCOUNT_TYPE("common.errors.user.account.not_allowed")
    ;
    
    public final String key;
    
    UserErrorMessageEnum(final String key) {
        this.key = key;
    }
}
