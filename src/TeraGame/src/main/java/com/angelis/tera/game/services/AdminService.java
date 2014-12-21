package com.angelis.tera.game.services;

import java.util.Map;
import java.util.Map.Entry;

import javolution.util.FastMap;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;

import com.angelis.tera.common.services.AbstractService;
import com.angelis.tera.game.command.admin.AbstractAdminCommand;
import com.angelis.tera.game.command.admin.AddCommand;
import com.angelis.tera.game.command.admin.AnnounceCommand;
import com.angelis.tera.game.command.admin.AppearCommand;
import com.angelis.tera.game.command.admin.BanCommand;
import com.angelis.tera.game.command.admin.DeSpawnCommand;
import com.angelis.tera.game.command.admin.DemoteCommand;
import com.angelis.tera.game.command.admin.GoToCommand;
import com.angelis.tera.game.command.admin.KickCommand;
import com.angelis.tera.game.command.admin.KillCommand;
import com.angelis.tera.game.command.admin.MountCommand;
import com.angelis.tera.game.command.admin.MovieCommand;
import com.angelis.tera.game.command.admin.PromoteCommand;
import com.angelis.tera.game.command.admin.QuestCommand;
import com.angelis.tera.game.command.admin.RecallCommand;
import com.angelis.tera.game.command.admin.ReloadCommand;
import com.angelis.tera.game.command.admin.RemoveCommand;
import com.angelis.tera.game.command.admin.SendFakePacketCommand;
import com.angelis.tera.game.command.admin.SetCommand;
import com.angelis.tera.game.command.admin.SpawnCommand;
import com.angelis.tera.game.command.admin.SummonCommand;
import com.angelis.tera.game.command.admin.TeleportCommand;
import com.angelis.tera.game.command.admin.UnMountCommand;
import com.angelis.tera.game.config.AdminConfig;
import com.angelis.tera.game.network.packet.server.SM_CHAT;
import com.angelis.tera.game.process.model.channel.enums.ChatTypeEnum;
import com.angelis.tera.game.process.model.player.Player;

public class AdminService extends AbstractService {

    /** LOGGER */
    private static Logger log = Logger.getLogger(AdminService.class.getName());

    private final Map<String, AbstractAdminCommand> commands = new FastMap<String, AbstractAdminCommand>();

    private AdminService() {
    }

    @Override
    public void onInit() {
        commands.put("announce", new AnnounceCommand());
        commands.put("add", new AddCommand());
        commands.put("remove", new RemoveCommand());
        commands.put("teleport", new TeleportCommand());
        commands.put("sendfakepacket", new SendFakePacketCommand());
        commands.put("promote", new PromoteCommand());
        commands.put("demote", new DemoteCommand());
        commands.put("set", new SetCommand());
        commands.put("reload", new ReloadCommand());
        commands.put("ban", new BanCommand());
        commands.put("kick", new KickCommand());
        commands.put("goto", new GoToCommand());
        commands.put("summon", new SummonCommand());
        commands.put("spawn", new SpawnCommand());
        commands.put("despawn", new DeSpawnCommand());
        commands.put("appear", new AppearCommand());
        commands.put("mount", new MountCommand());
        commands.put("unmount", new UnMountCommand());
        commands.put("movie", new MovieCommand());
        commands.put("quest", new QuestCommand());
        commands.put("kill", new KillCommand());
        commands.put("recall", new RecallCommand());

        log.info("AdminService started");
    }

    @Override
    public void onDestroy() {
        log.info("AdminService stopped");
    }

    public boolean onPlayerChat(final Player player, final String chat) {
        final String commandChat = chat.replace("<FONT>", "").replace("</FONT>", "");
        if (!commandChat.startsWith(AdminConfig.ADMIN_COMMAND_PREFIX)) {
            return false;
        }

        final String[] arguments = commandChat.split(" ");
        if (arguments.length == 0) {
            return false;
        }

        final String commandName = arguments[0].replace(AdminConfig.ADMIN_COMMAND_PREFIX, "");
        final String[] commandArguments = ArrayUtils.removeElement(arguments, arguments[0]);
        for (final Entry<String, AbstractAdminCommand> entry : commands.entrySet()) {
            if (commandName.startsWith(entry.getKey())) {
                if (player.getAccount().getAccess() >= entry.getValue().getAccessLevel()) {
                    if (commandArguments.length > 0 && "help".equals(commandArguments[0])) {
                        player.getConnection().sendPacket(new SM_CHAT(null, entry.getValue().getSyntax(), ChatTypeEnum.SYSTEM));
                        return true;
                    }

                    if (commandArguments.length < entry.getValue().getArgumentCount() || !entry.getValue().checkArguments(commandArguments)) {
                        player.getConnection().sendPacket(new SM_CHAT(null, entry.getValue().getSyntax(), ChatTypeEnum.SYSTEM));
                        return true;
                    }

                    entry.getValue().execute(player.getConnection(), commandArguments);
                    return true;
                }
            }
        }

        return false;
    }

    /** SINGLETON */
    public static AdminService getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        protected static final AdminService instance = new AdminService();
    }
}
