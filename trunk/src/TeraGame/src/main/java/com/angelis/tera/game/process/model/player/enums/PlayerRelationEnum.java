package com.angelis.tera.game.process.model.player.enums;

import org.apache.log4j.Logger;

public enum PlayerRelationEnum {
    FRIENDLY(1),
    PARTY_MEMBER(2),
    UNK_ENEMY(3),
    //Orange(alliance?) - 4
    DUEL_ENEMY(5),
    GUILD_MEMBER(6);
    //LightBlue - 7
    
    private final int value;
    
    PlayerRelationEnum(final int value) {
        this.value = value;
    }
    
    public int getValue() {
        return this.value;
    }
    
    public static PlayerRelationEnum fromValue(final int value) {
        for (final PlayerRelationEnum playerRelation : PlayerRelationEnum.values()) {
            if (playerRelation.value == value) {
                return playerRelation;
            }
        }
        
        Logger.getLogger(PlayerRelationEnum.class.getName()).error("Can't find PlayerRelationEnum with value "+value);
        return null;
    }
}
