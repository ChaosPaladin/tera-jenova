package com.angelis.tera.game.process.model.player.enums;

import org.apache.log4j.Logger;

public enum RaceEnum {
    HUMAN(0),
    HIGH_ELF(1),
    AMAN(2),
    CASTANIC(3),
    POPORI(4),
    ELIN(4),
    BARAKA(5);

    public final int value;

    RaceEnum(final int value) {
        this.value = value;
    }
    
    public static RaceEnum fromValue(final int value) {
        for (final RaceEnum race : RaceEnum.values()) {
            if (race.value == value) {
                return race;
            }
        }
        
        Logger.getLogger(RaceEnum.class.getName()).error("Can't find RaceEnum with value "+value);
        return null;
    }
}
