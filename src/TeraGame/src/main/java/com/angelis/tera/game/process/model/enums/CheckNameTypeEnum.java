package com.angelis.tera.game.process.model.enums;

import org.apache.log4j.Logger;

public enum CheckNameTypeEnum {

    CHARACTER(1, 2, 16, false),
    TARGET(9, 1, 1, false),
    STATUS(11, 2, 40, true);
    
    public final int code;
    public final int minLength;
    public final int maxLength;
    public final boolean spaceAllowed;
    
    CheckNameTypeEnum(final int code, final int minLength, final int maxLength, final boolean spaceAllowed) {
        this.code = code;
        this.minLength = minLength;
        this.maxLength = maxLength;
        this.spaceAllowed = spaceAllowed;
    }
    
    public static CheckNameTypeEnum fromCode(final int code) {
        for (final CheckNameTypeEnum checkNameType : CheckNameTypeEnum.values()) {
            if (checkNameType.code == code) {
                return checkNameType;
            }
        }
        
        Logger.getLogger(CheckNameTypeEnum.class.getName()).error("Can't find CheckNameTypeEnum with code "+code);
        return null;
    }
}
