package com.angelis.tera.game.process.model.player.enums;

import org.apache.log4j.Logger;

import com.angelis.tera.game.process.model.channel.enums.ChatTypeEnum;

public enum PlayerMoveTypeEnum {
    NORMAL(0),
    STUCK(2),
    JUMP_START(5),
    JUMP_BLOCK(6),
    JUMP_END(7),
    FALL(10);
    
    public final short value;
    
    PlayerMoveTypeEnum(final int value) {
        this.value = (short) value;
    }
    
    public static PlayerMoveTypeEnum fromValue(final short value) {
        for (final PlayerMoveTypeEnum playerMoveType : PlayerMoveTypeEnum.values()) {
            if (playerMoveType.value == value) {
                return playerMoveType;
            }
        }
        
        Logger.getLogger(ChatTypeEnum.class.getName()).error("Can't find PlayerMoveTypeEnum with value "+value);
        return null;
    }
}
