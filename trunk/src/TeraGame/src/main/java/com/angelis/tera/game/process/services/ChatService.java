package com.angelis.tera.game.process.services;

import org.apache.log4j.Logger;

import com.angelis.tera.common.process.services.AbstractService;
import com.angelis.tera.common.utils.Function;
import com.angelis.tera.game.config.PlayerConfig;
import com.angelis.tera.game.presentation.network.SystemMessages;
import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;
import com.angelis.tera.game.presentation.network.packet.server.SM_WHISP;
import com.angelis.tera.game.presentation.network.packet.server.chat.SM_CHAT;
import com.angelis.tera.game.process.controllers.enums.RightEnum;
import com.angelis.tera.game.process.exceptions.command.CommandEmptyException;
import com.angelis.tera.game.process.exceptions.command.NotEnoughAccessForCommand;
import com.angelis.tera.game.process.exceptions.command.NotEnoughArgumentForCommand;
import com.angelis.tera.game.process.exceptions.command.admin.NoSuchAdminCommandException;
import com.angelis.tera.game.process.exceptions.command.user.NoSuchUserCommandException;
import com.angelis.tera.game.process.exceptions.services.chat.MutedPlayerTryedToTalkException;
import com.angelis.tera.game.process.exceptions.services.chat.PlayerLevelToLowToUseThisChannelException;
import com.angelis.tera.game.process.exceptions.services.chat.PlayerNotInGroupException;
import com.angelis.tera.game.process.model.channel.enums.ChatTypeEnum;
import com.angelis.tera.game.process.model.group.Group;
import com.angelis.tera.game.process.model.group.LookingForGroupInfo;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.model.visible.VisibleTeraObject;
import com.angelis.tera.game.process.model.visible.enums.VisibleTypeEnum;

public class ChatService extends AbstractService {

    /** LOGGER */
    private static Logger log = Logger.getLogger(ChatService.class.getName());

    private ChatService() {
    }

    @Override
    public void onInit() {
        log.info("ChatService started");
    }

    @Override
    public void onDestroy() {
        log.info("ChatService stopped");
    }

    public void onPlayerChat(final Player player, final String chat, final ChatTypeEnum chatType)
    throws NotEnoughAccessForCommand, NotEnoughArgumentForCommand, PlayerLevelToLowToUseThisChannelException, MutedPlayerTryedToTalkException, PlayerNotInGroupException {
        // Chat is a admin command and player has access
        try {
            AdminService.getInstance().onPlayerChat(player, chat);
            return;
        }
        catch (NoSuchAdminCommandException | CommandEmptyException e) {
            // Nothing to do just continue chat process
        }

        // Chat is an user command
        try {
            UserService.getInstance().onPlayerChat(player, chat);
            return;
        }
        catch (NoSuchUserCommandException | CommandEmptyException e) {
            // Nothing to do just continue chat process
        }

        if (!player.getController().can(RightEnum.TALK)) {
            throw new MutedPlayerTryedToTalkException(player.getName());
        }

        if (chatType == ChatTypeEnum.GENERAL) {
            if (player.getAccount().getAccess() == 0 && player.getLevel() < PlayerConfig.PLAYER_LEVEL_MIN_GLOBAL_CHAT) {
                throw new PlayerLevelToLowToUseThisChannelException();
            }
        }

        final TeraServerPacket packet = new SM_CHAT(player, chat, chatType);
        switch (chatType) {
            case SAY:
                for (final VisibleTeraObject visibleTeraObject : player.getKnownList().getVisible(VisibleTypeEnum.PLAYER)) {
                    ((Player) visibleTeraObject).getConnection().sendPacket(packet);
                }
                player.getConnection().sendPacket(packet);
            break;

            case AREA:
                final int areaId = WorldService.getInstance().getAreaByMapId(player.getWorldPosition().getMapId());
                for (final Player observer : WorldService.getInstance().getPlayersByArea(areaId)) {
                    observer.getConnection().sendPacket(packet);
                }
            break;

            case PARTY:
                final Group group = player.getGroup();
                if (group == null) {
                    throw new PlayerNotInGroupException();
                }

                group.doOnEachPlayer(new Function<Player>() {
                    @Override
                    public void call(final Player player) {
                        player.getConnection().sendPacket(packet);
                    }
                });
            break;

            default:
                WorldService.getInstance().doOnAllOnlinePlayer(new Function<Player>() {
                    @Override
                    public void call(final Player player) {
                        player.getConnection().sendPacket(packet);
                    }
                });
        }
    }

    public void onPlayerWhisper(final Player player, final String targetName, final String whisper) throws NotEnoughArgumentForCommand {
        // Chat is a admin command and player has access
        try {
            AdminService.getInstance().onPlayerChat(player, whisper);
        }
        catch (NoSuchAdminCommandException | CommandEmptyException e) {
            // Nothing to do just continue chat process
        }


        final Player targetPlayer = WorldService.getInstance().getOnlinePlayerByName(targetName);
        if (targetPlayer == null) {
            player.getConnection().sendPacket(SystemMessages.WHISP_PLAYER_NOT_ONLINE());
            return;
        }

        if (targetPlayer.equals(player)) {
            player.getConnection().sendPacket(SystemMessages.WHISP_INVALID_TARGET());
            return;
        }

        if (targetPlayer.getBlocks().contains(player)) {
            player.getConnection().sendPacket(SystemMessages.CHARACTER_IGNORE_YOUR_WHISP(targetPlayer.getName()));
            return;
        }

        final TeraServerPacket packet = new SM_WHISP(player, targetPlayer, whisper);
        player.getConnection().sendPacket(packet);
        targetPlayer.getConnection().sendPacket(packet);
    }
    
    public void onPlayerRequestLookingForGroupInfo(final Player player, final LookingForGroupInfo lookingForGroupInfo) {
        if (lookingForGroupInfo == null) {
            // TODO
            return;
        }
        
        
    }

    /** SINGLETON */
    public static ChatService getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        protected static final ChatService instance = new ChatService();
    }
}
