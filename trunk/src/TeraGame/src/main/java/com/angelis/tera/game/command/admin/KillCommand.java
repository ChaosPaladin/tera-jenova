package com.angelis.tera.game.command.admin;

import java.util.List;

import javolution.util.FastList;

import com.angelis.tera.game.command.AdminErrorMessageEnum;
import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.model.visible.VisibleTeraObject;
import com.angelis.tera.game.process.model.visible.enums.VisibleTypeEnum;
import com.angelis.tera.game.process.services.WorldService;

public class KillCommand extends AbstractAdminCommand {
    
    private enum CommandEnum {
        MONSTERS,
        PLAYER,
        PLAYERS,
        ALL,
        ;
    }
    
    @Override
    public void execute(final TeraGameConnection connection, final String[] arguments) {
        final List<VisibleTeraObject> killed = new FastList<>();
        final CommandEnum command = CommandEnum.valueOf(arguments[0].toUpperCase());
        switch (command) {
            case MONSTERS:
                killed.addAll(connection.getActivePlayer().getKnownList().getVisible(VisibleTypeEnum.MONSTER));
            break;

            case PLAYERS:
                killed.addAll(connection.getActivePlayer().getKnownList().getVisible(VisibleTypeEnum.PLAYER));
            break;

            case PLAYER:
                final Player player = WorldService.getInstance().getOnlinePlayerByName(arguments[1]);
                if (player == null) {
                    this.sendErrorMessage(connection, AdminErrorMessageEnum.TARGET_NOT_FOUND.key);
                    return;
                }
                
                killed.add(player);
            break;
            
            case ALL:
                killed.addAll(connection.getActivePlayer().getKnownList().getVisible(VisibleTypeEnum.MONSTER));
                killed.addAll(connection.getActivePlayer().getKnownList().getVisible(VisibleTypeEnum.PLAYER));
            break;
        }
        
        for (final VisibleTeraObject visible : killed) {
            visible.getController().onDie(connection.getActivePlayer());
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
            CommandEnum.valueOf(arguments[0].toUpperCase());
            return true;
        }
        catch (final Exception e) {
            return false;
        }
    }

    @Override
    public String getSyntax() {
        return "kill {monsters | players | all | player {name}}";
    }
}
