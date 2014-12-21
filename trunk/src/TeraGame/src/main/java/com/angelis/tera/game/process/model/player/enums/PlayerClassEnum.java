package com.angelis.tera.game.process.model.player.enums;

import org.apache.log4j.Logger;

public enum PlayerClassEnum {
    WARRIOR(0),
    LANCER(1),
    SLAYER(2),
    BERSERKER(3),
    SORCERER(4),
    ARCHER(5),
    PRIEST(6),
    MYSTIC(7),
    REAPER(8)
    ;
    
    public final int value;

    PlayerClassEnum(final int value) {
        this.value = value;
    }
    
    public static PlayerClassEnum fromValue(final int value) {
        for (final PlayerClassEnum playerClass : PlayerClassEnum.values()) {
            if (playerClass.value == value) {
                return playerClass;
            }
        }
        
        Logger.getLogger(PlayerClassEnum.class.getName()).error("Can't find PlayerClassEnum with value "+value);
        return null;
    }
}
