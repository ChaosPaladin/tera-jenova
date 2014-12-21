package com.angelis.tera.game.command.admin;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.model.visible.WorldPosition;
import com.angelis.tera.game.process.services.PlayerService;

public class RecallCommand extends AbstractAdminCommand {
    
    private enum CommandEnum {
        ADD,
        GO,
        ;
    }

    @Override
    public void execute(final TeraGameConnection connection, final String[] arguments) {
        final Player player = connection.getActivePlayer();
        final CommandEnum command = CommandEnum.valueOf(arguments[0].toUpperCase());
        final String recallName = arguments[1];
        
        switch (command) {
            case ADD:
                player.getPlayerCustomData().getRecalls().put(recallName, player.getWorldPosition().clone());
            break;

            case GO:
                final WorldPosition worldPosition = player.getPlayerCustomData().getRecalls().get(recallName);
                if (worldPosition == null) {
                    this.sendErrorMessage(connection, "TODO ERROR MESSAGE");
                    return;
                }
                
                PlayerService.getInstance().teleportPlayer(player, worldPosition);
            break;
        }
    }

    @Override
    public int getAccessLevel() {
        return 1;
    }

    @Override
    public int getArgumentCount() {
        return 2;
    }

    @Override
    public boolean checkArguments(final String[] arguments) {
        try {
            CommandEnum.valueOf(arguments[0].toUpperCase());
            return true;
        }
        catch (final Exception e) {
            return false;
        }
    }

    @Override
    public String getSyntax() {
        return "add {right | craft | gather | money | item | storage | skill} [targetName] [value]";
    }
}
