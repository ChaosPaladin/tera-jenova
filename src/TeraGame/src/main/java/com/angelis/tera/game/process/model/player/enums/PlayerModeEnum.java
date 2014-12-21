package com.angelis.tera.game.process.model.player.enums;

public enum PlayerModeEnum {
    NORMAL(0x00),
    ARMORED(0x01),
    RELAX(0x02),
    FLYING(0x03);
    
    public final int value;
    
    PlayerModeEnum(final int value) {
        this.value = value;
    }
}
