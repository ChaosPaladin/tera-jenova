package com.angelis.tera.game.command.admin;

import com.angelis.tera.game.command.AdminErrorMessageEnum;
import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.process.model.account.Account;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.model.visible.WorldPosition;
import com.angelis.tera.game.process.services.PlayerService;
import com.angelis.tera.game.process.services.WorldService;

public class SummonCommand extends AbstractAdminCommand {
    @Override
    public void execute(final TeraGameConnection connection, final String[] arguments) {
        final Player targetPlayer = WorldService.getInstance().getOnlinePlayerByName(arguments[0]);
        if (targetPlayer == null) {
            this.sendTranslatedErrorMessage(connection, AdminErrorMessageEnum.TARGET_NOT_FOUND.key);
            return;
        }
        
        final Account targetAccount = targetPlayer.getAccount();
        if (targetAccount.getAccess() > connection.getAccount().getAccess()) {
            this.sendTranslatedErrorMessage(connection, AdminErrorMessageEnum.TARGET_HAS_MORE_RIGHTS_THAN_YOU.key);
            return;
        }
        
        final WorldPosition worldPosition = connection.getActivePlayer().getWorldPosition();
        PlayerService.getInstance().teleportPlayer(targetPlayer, worldPosition);
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
        return "summon [targetName]";
    }
}
