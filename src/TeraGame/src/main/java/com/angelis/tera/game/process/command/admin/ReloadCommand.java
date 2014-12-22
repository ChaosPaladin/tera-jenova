package com.angelis.tera.game.process.command.admin;

import com.angelis.tera.common.utils.Function;
import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.ClientPacketHandler;
import com.angelis.tera.game.presentation.network.packet.ServerPacketHandler;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.services.AdminService;
import com.angelis.tera.game.process.services.DropService;
import com.angelis.tera.game.process.services.MountService;
import com.angelis.tera.game.process.services.PegasusFlyService;
import com.angelis.tera.game.process.services.QuestService;
import com.angelis.tera.game.process.services.SpawnService;
import com.angelis.tera.game.process.services.TemplateService;
import com.angelis.tera.game.process.services.TradelistService;
import com.angelis.tera.game.process.services.UserService;
import com.angelis.tera.game.process.services.WorldService;
import com.angelis.tera.game.process.services.XMLService;
import com.angelis.tera.game.process.services.ZoneService;

public class ReloadCommand extends AbstractAdminCommand {

    private enum Command {
        NETWORK,
        XML,
        COMMANDS
    }
    
    @Override
    public void execute(final TeraGameConnection connection, final String[] arguments) {
        
        switch (Command.valueOf(arguments[0].toUpperCase())) {
            case NETWORK:
                ServerPacketHandler.init();
                ClientPacketHandler.init();
            break;
            
            case XML:
                XMLService.getInstance().restart();
                TemplateService.getInstance().restart();
                SpawnService.getInstance().restart();
                MountService.getInstance().restart();
                QuestService.getInstance().restart();
                DropService.getInstance().restart();
                TradelistService.getInstance().restart();
                PegasusFlyService.getInstance().restart();
                ZoneService.getInstance().restart();
                WorldService.getInstance().doOnAllOnlinePlayer(new Function<Player>() {
                    @Override
                    public void call(final Player player) {
                        player.getKnownList().clear();
                        player.getKnownList().update();
                    }
                });
            break;
            
            case COMMANDS:
                AdminService.getInstance().restart();
                UserService.getInstance().restart();
            break;
        }
    }

    @Override
    public int getAccessLevel() {
        return 1;
    }

    @Override
    public int getArgumentCount() {
        return 1;
    }

    @Override
    public boolean checkArguments(final String[] arguments) {
        try {
            Command.valueOf(arguments[0].toUpperCase());
            return true;
        } catch (final Exception e) {
            return false;
        }
    }

    @Override
    public String getSyntax() {
        return "reload {network|xml|commands}";
    }
}
