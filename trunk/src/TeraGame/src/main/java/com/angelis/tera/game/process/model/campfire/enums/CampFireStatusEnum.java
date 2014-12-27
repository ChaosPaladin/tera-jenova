package com.angelis.tera.game.process.model.campfire.enums;

import org.apache.log4j.Logger;

public enum CampFireStatusEnum {
    NORMAL(0)
    ;

    public final int value;

    CampFireStatusEnum(final int value) {
        this.value = value;
    }

    public static CampFireStatusEnum fromValue(final int value) {
        for (final CampFireStatusEnum campFireStatus : CampFireStatusEnum.values()) {
            if (campFireStatus.value == value) {
                return campFireStatus;
            }
        }
        
        Logger.getLogger(CampFireTypeEnum.class.getName()).error("Can't find CampFireStatusEnum with value "+value);
        return null;
    }
}
