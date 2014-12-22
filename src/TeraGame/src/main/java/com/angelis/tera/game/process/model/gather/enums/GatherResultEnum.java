package com.angelis.tera.game.process.model.gather.enums;

import org.apache.log4j.Logger;

public enum GatherResultEnum {
    RUPTED(1),
    FAILED(2),
    NORMAL(3)
    ;
    
    public final int value;

    private GatherResultEnum(final int value) {
        this.value = value;
    }
    
    public static GatherResultEnum fromValue(final int value) {
        for (final GatherResultEnum gatherResult : GatherResultEnum.values()) {
            if (gatherResult.value == value) {
                return gatherResult;
            }
        }
        
        Logger.getLogger(GatherResultEnum.class.getName()).error("Can't find GatherResultEnum with value "+value);
        return null;
    }
}
