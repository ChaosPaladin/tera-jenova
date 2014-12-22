package com.angelis.tera.game.process.model.storage.enums;

import org.apache.log4j.Logger;

public enum ViewModeEnum {
    TRY_ON(17),
    INVENTORY(20),
    INSPECT(24)
    ;
    
    public final int value;
    
    ViewModeEnum(final int value) {
        this.value = value;
    }

    public static ViewModeEnum fromValue(final int value) {
        for (final ViewModeEnum viewMode : ViewModeEnum.values()) {
            if (viewMode.value == value) {
                return viewMode;
            }
        }
        
        Logger.getLogger(ViewModeEnum.class.getName()).error("Can't find ViewModeEnum with value "+value);
        return null;
    }
}
