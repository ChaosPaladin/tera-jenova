package com.angelis.tera.game.presentation.network.packet.client;

import java.nio.ByteBuffer;

import com.angelis.tera.game.config.PlayerConfig;
import com.angelis.tera.game.presentation.network.SystemMessages;
import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraClientPacket;
import com.angelis.tera.game.presentation.network.packet.server.SM_CHAT;
import com.angelis.tera.game.process.command.UserErrorMessageEnum;
import com.angelis.tera.game.process.exceptions.command.NotEnoughAccessForCommand;
import com.angelis.tera.game.process.exceptions.command.NotEnoughArgumentForCommand;
import com.angelis.tera.game.process.exceptions.services.chat.MutedPlayerTryedToTalkException;
import com.angelis.tera.game.process.exceptions.services.chat.PlayerLevelToLowToUseThisChannelException;
import com.angelis.tera.game.process.exceptions.services.chat.PlayerNotInGroupException;
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
        final TeraGameConnection connection = this.getConnection();
        final Player player = connection.getActivePlayer();

        try {
            ChatService.getInstance().onPlayerChat(player, this.chat, this.chatType);
        }
        catch (final NotEnoughAccessForCommand e) {
            // TODO exceptions for user and admin
            connection.sendPacket(new SM_CHAT(player, I18nService.getInstance().translate(player.getAccount().getLocale(), UserErrorMessageEnum.NOT_ALLOWED_ACCOUNT_TYPE.key), ChatTypeEnum.SYSTEM));
        }
        catch (final NotEnoughArgumentForCommand e) {
            connection.sendPacket(new SM_CHAT(player, e.getCommand().getSyntax(), ChatTypeEnum.SYSTEM));
        }
        catch (final PlayerLevelToLowToUseThisChannelException e) {
            connection.sendPacket(SystemMessages.CHARACTER_MUST_BE_LEVEL_TO_USE_THIS_CHANNEL(String.valueOf(PlayerConfig.PLAYER_LEVEL_MIN_GLOBAL_CHAT)));
        }
        catch (final MutedPlayerTryedToTalkException e) {
            connection.sendPacket(new SM_CHAT(player, I18nService.getInstance().translate(player.getAccount().getLocale(), "common.errors.chat.muted"), ChatTypeEnum.SYSTEM));
        }
        catch (final PlayerNotInGroupException e) {
            connection.sendPacket(SystemMessages.YOU_ARE_NOT_IN_A_GROUP());
        }
    }
}
