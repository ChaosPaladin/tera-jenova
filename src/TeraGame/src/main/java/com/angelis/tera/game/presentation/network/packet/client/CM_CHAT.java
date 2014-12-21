package com.angelis.tera.game.presentation.network.packet.client;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraClientPacket;
import com.angelis.tera.game.presentation.network.packet.server.SM_CHAT;
import com.angelis.tera.game.process.exceptions.MutedPlayerTryedToTalkException;
import com.angelis.tera.game.process.model.channel.enums.ChatTypeEnum;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.services.ChatService;
import com.angelis.tera.game.process.services.I18nService;

public class CM_CHAT extends TeraClientPacket {

    private String chat;
    private ChatTypeEnum chatType;
    
    public CM_CHAT(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        readH(); // length
        
        this.chatType = ChatTypeEnum.fromValue(readD());
        this.chat = readS();
    }

    @Override
    protected void runImpl() {
        final Player player = this.getConnection().getActivePlayer();
        try {
            ChatService.getInstance().onPlayerChat(player, this.chat, this.chatType);
        }
        catch (final MutedPlayerTryedToTalkException e) {
            log.info(e.getMessage());
            this.getConnection().sendPacket(new SM_CHAT(player, I18nService.getInstance().translate(player.getAccount().getLocale(), "common.errors.chat.muted"), ChatTypeEnum.SYSTEM));
            return;
        }
    }
}
