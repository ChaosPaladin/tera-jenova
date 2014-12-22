package com.angelis.tera.game.process.model.drop.enums;

// TODO stats must not be good !!!!!!!!!!!!!!
public enum DropChanceEnum {
    HIGH(20),
    MEDIUM(50),
    LOW(75),
    VERY_LOW(80),
    RARE(90)
    ;
    
    public final int value;
    
    DropChanceEnum(final int value) {
        this.value = value;
    }
}
