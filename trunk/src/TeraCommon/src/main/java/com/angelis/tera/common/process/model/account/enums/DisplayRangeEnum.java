package com.angelis.tera.common.process.model.account.enums;

import org.apache.log4j.Logger;

public enum DisplayRangeEnum {
    MIN(1000),
    VERY_LOW(1200),
    LOW(1400),
    MEDIUM(1600),
    HIGH(1800),
    VERY_HIGH(2000),
    MAX(2500);
    
    public final int value;
    
    DisplayRangeEnum(final int value) {
        this.value = value;
    }

    public static DisplayRangeEnum fromValue(final int value) {
        for (final DisplayRangeEnum displayRange : DisplayRangeEnum.values()) {
            if (displayRange.value == value) {
                return displayRange;
            }
        }
        
        Logger.getLogger(DisplayRangeEnum.class.getName()).error("Can't find DysplayRangeEnum with value "+value);
        return null;
    }
}
