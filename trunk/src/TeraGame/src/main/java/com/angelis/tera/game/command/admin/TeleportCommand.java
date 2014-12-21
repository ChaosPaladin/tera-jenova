package com.angelis.tera.game.command.admin;

import com.angelis.tera.game.command.AdminErrorMessageEnum;
import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.services.PlayerService;
import com.angelis.tera.game.process.services.WorldService;

public class TeleportCommand extends AbstractAdminCommand {

    @Override
    public void execute(final TeraGameConnection connection, final String[] arguments) {
        final Player targetPlayer = WorldService.getInstance().getOnlinePlayerByName(arguments[0]);
        if (targetPlayer == null) {
            this.sendTranslatedErrorMessage(connection, AdminErrorMessageEnum.TARGET_NOT_FOUND.key);
            return;
        }
        
        PlayerService.getInstance().teleportPlayer(targetPlayer, Integer.parseInt(arguments[1]), Float.parseFloat(arguments[2]), Float.parseFloat(arguments[3]), Float.parseFloat(arguments[4]));
    }

    @Override
    public int getAccessLevel() {
        return 1;
    }

    @Override
    public int getArgumentCount() {
        return 5;
    }
    
    @Override
    public boolean checkArguments(final String[] arguments) {
        try {
            Integer.parseInt(arguments[1]);
            Float.parseFloat(arguments[2]);
            Float.parseFloat(arguments[3]);
            Float.parseFloat(arguments[4]);
            return true;
        } catch (final Exception e) {
            return false;
        }
    }

    @Override
    public String getSyntax() {
        return "teleport [targetName] [mapId] [x] [y] [z]";
    }
}
