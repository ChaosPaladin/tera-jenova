package com.angelis.tera.game.process.model.campfire.enums;

import org.apache.log4j.Logger;

public enum CampFireTypeEnum {
    NORMAL(1)
    ;
    
    public final int value;

    CampFireTypeEnum(final int value) {
        this.value = value;
    }

    public static CampFireTypeEnum fromValue(final int value) {
        for (final CampFireTypeEnum campFireType : CampFireTypeEnum.values()) {
            if (campFireType.value == value) {
                return campFireType;
            }
        }
        
        Logger.getLogger(CampFireTypeEnum.class.getName()).error("Can't find CampFireTypeEnum with value "+value);
        return null;
    }
}
