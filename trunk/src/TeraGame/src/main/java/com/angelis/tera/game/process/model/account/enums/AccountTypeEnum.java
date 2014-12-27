package com.angelis.tera.game.process.model.account.enums;

public enum AccountTypeEnum {
    NORMAL(2),
    VETERAN(8),
    PREMIUM(8);
    
    public final int maxPlayerCount;
    
    AccountTypeEnum(final int maxPlayerCount) {
        this.maxPlayerCount = maxPlayerCount;
    }
}
