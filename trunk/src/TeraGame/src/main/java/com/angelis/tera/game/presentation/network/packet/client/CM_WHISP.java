package com.angelis.tera.game.presentation.network.packet.client;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraClientPacket;
import com.angelis.tera.game.presentation.network.packet.server.chat.SM_CHAT;
import com.angelis.tera.game.process.command.UserErrorMessageEnum;
import com.angelis.tera.game.process.exceptions.command.NotEnoughArgumentForCommand;
import com.angelis.tera.game.process.model.channel.enums.ChatTypeEnum;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.services.ChatService;
import com.angelis.tera.game.process.services.I18nService;

public class CM_WHISP extends TeraClientPacket {

    private String name;
    private String whisper;
    
    public CM_WHISP(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        readH(); // 08 00
        readH(); // 16 00
        this.name = readS();
        this.whisper = readS();
    }

    @Override
    protected void runImpl() {
        final TeraGameConnection connection = this.getConnection();
        final Player player = connection.getActivePlayer();
        
        try {
            ChatService.getInstance().onPlayerWhisper(player, this.name, this.whisper);
        }
        catch (final NotEnoughArgumentForCommand e) {
            connection.sendPacket(new SM_CHAT(player, I18nService.getInstance().translate(player.getAccount().getLocale(), UserErrorMessageEnum.NOT_ALLOWED_ACCOUNT_TYPE.key), ChatTypeEnum.SYSTEM));
        }
    }
}
