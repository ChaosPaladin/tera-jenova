package com.angelis.tera.game.process.model.player.enums;

import org.apache.log4j.Logger;

public enum GenderEnum {
    MALE(0),
    FEMALE(1);

    public final int value;
    
    GenderEnum(final int value) {
        this.value = value;
    }
    
    public static GenderEnum fromValue(final int value) {
        for (final GenderEnum gender : GenderEnum.values()) {
            if (gender.value == value) {
                return gender;
            }
        }
        
        Logger.getLogger(GenderEnum.class.getName()).error("Can't find GenderEnum with value "+value);
        return null;
    }
}
