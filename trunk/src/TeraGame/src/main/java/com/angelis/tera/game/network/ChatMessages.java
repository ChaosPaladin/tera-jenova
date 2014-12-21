package com.angelis.tera.game.network;

import com.angelis.tera.game.network.packet.server.SM_CHAT;
import com.angelis.tera.game.process.model.channel.enums.ChatTypeEnum;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.model.player.enums.EmoteEnum;

public class ChatMessages {
    public static SM_CHAT emote(final Player player, final EmoteEnum emote) {
        return new SM_CHAT(player, "@social:10"+emote.value, ChatTypeEnum.SOCIAL);
    }
}
