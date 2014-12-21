package com.angelis.tera.game.process.model.enums;

public enum DespawnTypeEnum {
    GATHERED(0),
    DELETE(1),
    DEATH(5);
    
    public final int value;
    
    DespawnTypeEnum(final int value) {
        this.value = value;
    }
}
