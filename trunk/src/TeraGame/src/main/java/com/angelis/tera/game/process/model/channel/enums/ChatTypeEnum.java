package com.angelis.tera.game.process.model.channel.enums;

import org.apache.log4j.Logger;

public enum ChatTypeEnum {

    SAY(0),
    PARTY(1),
    GUILD(2),
    AREA(3),
    TRADE(4),
    TEAM(5),
    CLUB(6),
    PRIVATE_HEARD(7),
    PRIVATE_WHISPERED(8),
    BARGAIN(9), // TODO
    RAID_LEADER(10),
    CHANNEL1(11),
    CHANNEL2(12),
    CHANNEL3(13),
    CHANNEL4(14),
    CHANNEL5(15),
    CHANNEL6(16),
    CHANNEL7(17),
    CHANNEL8(18),
    MERCHANT(19),
    LOOKING_FOR_GROUP(20),
    NOTICE(21),
    VARNARCH_ANNOUNCEMENT(22),
    SYSTEM(24),
    RAID_LEADER_ANNOUCEMENT(25),
    SOCIAL(26),
    GENERAL(27);

    public final int value;

    ChatTypeEnum(final int value) {
        this.value = value;
    }

    public static ChatTypeEnum fromValue(final int value) {
        for (final ChatTypeEnum chatType : ChatTypeEnum.values()) {
            if (chatType.value == value) {
                return chatType;
            }
        }

        Logger.getLogger(ChatTypeEnum.class.getName()).error("Can't find ChatTypeEnum with value " + value);
        return null;
    }
}
