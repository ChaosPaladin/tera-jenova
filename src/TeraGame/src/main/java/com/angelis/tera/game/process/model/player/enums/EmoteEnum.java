package com.angelis.tera.game.process.model.player.enums;

import org.apache.log4j.Logger;

public enum EmoteEnum {
    GREET(16),
    ARC(17),
    SAD(18),
    HAPPY(19),
    VICTORY(20),
    DANSE(21),
    LAUGH(22),
    APPLAUSE(23),
    IMPLORE(24),
    THINK(25),
    LOVE(26),
    ATTACK(27),
    SHOW(28),
    SHY(29),
    ANGRY(30),
    TALK(34),
    SIT(38),
    ;
    
    public final int value;
    
    EmoteEnum(final int value) {
        this.value = value;
    }

    public static EmoteEnum fromValue(final int value) {
        for (final EmoteEnum emote : EmoteEnum.values()) {
            if (emote.value == value) {
                return emote;
            }
        }
        
        Logger.getLogger(EmoteEnum.class.getName()).error("Can't find EmoteEnum with value "+value);
        return null;
    }
}
