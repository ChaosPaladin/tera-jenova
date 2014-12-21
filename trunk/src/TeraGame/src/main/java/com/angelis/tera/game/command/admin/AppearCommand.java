package com.angelis.tera.game.command.admin;

import com.angelis.tera.game.command.AdminErrorMessageEnum;
import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.model.visible.WorldPosition;
import com.angelis.tera.game.services.PlayerService;
import com.angelis.tera.game.services.WorldService;

public class AppearCommand extends AbstractAdminCommand {
    
    @Override
    public void execute(final TeraGameConnection connection, final String[] arguments) {
        final Player targetPlayer = WorldService.getInstance().getOnlinePlayerByName(arguments[0]);
        if (targetPlayer == null) {
            this.sendTranslatedErrorMessage(connection, AdminErrorMessageEnum.TARGET_NOT_FOUND.key);
            return;
        }

        final WorldPosition worldPosition = targetPlayer.getWorldPosition();
        PlayerService.getInstance().teleportPlayer(connection.getActivePlayer(), worldPosition);
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
        return true;
    }

    @Override
    public String getSyntax() {
        return "appear [targetName]";
    }
}
