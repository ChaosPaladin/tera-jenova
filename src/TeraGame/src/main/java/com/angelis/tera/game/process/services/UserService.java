package com.angelis.tera.game.process.services;

import java.util.Map;
import java.util.Map.Entry;

import javolution.util.FastMap;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;

import com.angelis.tera.common.services.AbstractService;
import com.angelis.tera.game.command.UserErrorMessageEnum;
import com.angelis.tera.game.command.user.AbstractUserCommand;
import com.angelis.tera.game.command.user.GpsCommand;
import com.angelis.tera.game.command.user.LangCommand;
import com.angelis.tera.game.command.user.SaveCommand;
import com.angelis.tera.game.config.UserConfig;
import com.angelis.tera.game.presentation.network.packet.server.SM_CHAT;
import com.angelis.tera.game.process.model.channel.enums.ChatTypeEnum;
import com.angelis.tera.game.process.model.player.Player;

public class UserService extends AbstractService {

    /** LOGGER */
    private static Logger log = Logger.getLogger(UserService.class.getName());

    private final Map<String, AbstractUserCommand> commands = new FastMap<String, AbstractUserCommand>();

    private UserService() {
    }
    
    @Override
    public void onInit() {
        commands.put("save", new SaveCommand());
        commands.put("lang", new LangCommand());
        commands.put("gps", new GpsCommand());

        log.info("UserService started");
    }

    @Override
    public void onDestroy() {
        log.info("UserService stopped");
    }

    public boolean onPlayerChat(final Player player, final String chat) {
        final String commandChat = chat.replace("<FONT>", "").replace("</FONT>", "");
        if (!commandChat.startsWith(UserConfig.USER_COMMAND_PREFIX)) {
            return false;
        }

        final String[] arguments = commandChat.split(" ");
        if (arguments.length == 0) {
            return false;
        }

        final String commandName = arguments[0].replace(UserConfig.USER_COMMAND_PREFIX, "");
        final String[] commandArguments = ArrayUtils.removeElement(arguments, arguments[0]);
        for (final Entry<String, AbstractUserCommand> entry : commands.entrySet()) {
            if (commandName.startsWith(entry.getKey())) {
                if (!entry.getValue().getAllowedAccountTypes().contains(player.getAccount().getAccountType())) {
                    player.getConnection().sendPacket(new SM_CHAT(player, I18nService.getInstance().translate(player.getAccount().getLocale(), UserErrorMessageEnum.NOT_ALLOWED_ACCOUNT_TYPE.key), ChatTypeEnum.SYSTEM));
                    return true;
                }

                if (commandArguments.length < entry.getValue().getArgumentCount() || !entry.getValue().checkArguments(commandArguments)) {
                    player.getConnection().sendPacket(new SM_CHAT(player, entry.getValue().getSyntax(), ChatTypeEnum.SYSTEM));
                    return true;
                }

                entry.getValue().execute(player.getConnection(), commandArguments);
                return true;
            }
        }

        return false;
    }

    /** SINGLETON */
    public static UserService getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        protected static final UserService instance = new UserService();
    }
}
