package com.angelis.tera.game.process.services;

import java.util.Map;
import java.util.Map.Entry;

import javolution.util.FastMap;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;

import com.angelis.tera.common.services.AbstractService;
import com.angelis.tera.game.config.UserConfig;
import com.angelis.tera.game.process.command.user.AbstractUserCommand;
import com.angelis.tera.game.process.command.user.GpsCommand;
import com.angelis.tera.game.process.command.user.LangCommand;
import com.angelis.tera.game.process.command.user.SaveCommand;
import com.angelis.tera.game.process.exceptions.command.CommandEmptyException;
import com.angelis.tera.game.process.exceptions.command.NotEnoughAccessForCommand;
import com.angelis.tera.game.process.exceptions.command.NotEnoughArgumentForCommand;
import com.angelis.tera.game.process.exceptions.command.user.NoSuchUserCommandException;
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

    public void onPlayerChat(final Player player, final String chat) throws NoSuchUserCommandException, CommandEmptyException, NotEnoughAccessForCommand, NotEnoughArgumentForCommand {
        final String commandChat = chat.replace("<FONT>", "").replace("</FONT>", "");
        if (!commandChat.startsWith(UserConfig.USER_COMMAND_PREFIX)) {
            throw new NoSuchUserCommandException(commandChat);
        }

        final String[] arguments = commandChat.split(" ");
        if (arguments.length == 0) {
            throw new CommandEmptyException();
        }

        final String commandName = arguments[0].replace(UserConfig.USER_COMMAND_PREFIX, "");
        final String[] commandArguments = ArrayUtils.removeElement(arguments, arguments[0]);
        for (final Entry<String, AbstractUserCommand> entry : commands.entrySet()) {
            if (commandName.startsWith(entry.getKey())) {
                if (!entry.getValue().getAllowedAccountTypes().contains(player.getAccount().getAccountType())) {
                    throw new NotEnoughAccessForCommand(commandName);
                }

                if (commandArguments.length < entry.getValue().getArgumentCount() || !entry.getValue().checkArguments(commandArguments)) {
                    throw new NotEnoughArgumentForCommand(entry.getValue());
                }

                entry.getValue().execute(player.getConnection(), commandArguments);
                break;
            }
        }
    }

    /** SINGLETON */
    public static UserService getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        protected static final UserService instance = new UserService();
    }
}
