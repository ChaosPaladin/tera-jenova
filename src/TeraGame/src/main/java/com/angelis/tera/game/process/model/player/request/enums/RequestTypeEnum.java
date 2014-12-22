package com.angelis.tera.game.process.model.player.request.enums;

import org.apache.log4j.Logger;

public enum RequestTypeEnum {
    PLAYER_TRADE(3),
    PARTY_INVITE(4),
    MAILBOX(8),
    DUEL(12),
    PEGASUS(15),
    TELEPORT(16),
    DEATH_MATCH_INVITE(19),
    NPC_TRADE(768),
    ;
    
    public final int value;
    
    RequestTypeEnum(final int value) {
        this.value = value;
    }

    public static RequestTypeEnum fromValue(final short value) {
        for (final RequestTypeEnum requestType : RequestTypeEnum.values()) {
            if (requestType.value == value) {
                return requestType;
            }
        }
        
        Logger.getLogger(RequestTypeEnum.class.getName()).error("Can't find RequestTypeEnum with value "+value);
        return null;
    }
}
