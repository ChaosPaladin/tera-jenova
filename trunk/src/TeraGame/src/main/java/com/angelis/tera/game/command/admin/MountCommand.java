package com.angelis.tera.game.command.admin;

import com.angelis.tera.game.command.AdminErrorMessageEnum;
import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.process.model.account.Account;
import com.angelis.tera.game.process.model.mount.Mount;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.services.MountService;
import com.angelis.tera.game.process.services.WorldService;

public class MountCommand extends AbstractAdminCommand {

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

        if (targetPlayer.getActiveMount() != null) {
            MountService.getInstance().onPlayerUnMount(targetPlayer);
        }

        final Mount mount = MountService.getInstance().getMountByMountIdAndSkillId(Integer.parseInt(arguments[1]), Integer.parseInt(arguments[2]));
        if (mount == null) {
            this.sendTranslatedErrorMessage(connection, AdminErrorMessageEnum.THIS_MOUNT_SKILL_DOES_NOT_EXIST.key);
            return;
        }

        MountService.getInstance().onPlayerMount(targetPlayer, mount);
    }

    @Override
    public int getAccessLevel() {
        return 1;
    }

    @Override
    public int getArgumentCount() {
        return 3;
    }

    @Override
    public boolean checkArguments(final String[] arguments) {
        try {
            Integer.parseInt(arguments[1]);
            return true;
        }
        catch (final Exception e) {
            return false;
        }
    }

    @Override
    public String getSyntax() {
        return "mount [targetName] [mountId] [mountSkillId]";
    }
}
