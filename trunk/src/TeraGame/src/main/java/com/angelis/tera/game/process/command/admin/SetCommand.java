package com.angelis.tera.game.process.command.admin;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.server.SM_LOAD_TOPO;
import com.angelis.tera.game.presentation.network.packet.server.SM_PLAYER_EXPERIENCE_UPDATE;
import com.angelis.tera.game.presentation.network.packet.server.SM_PLAYER_STATS_UPDATE;
import com.angelis.tera.game.process.command.AdminErrorMessageEnum;
import com.angelis.tera.game.process.model.account.Account;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.services.PlayerService;
import com.angelis.tera.game.process.services.WorldService;

public class SetCommand extends AbstractAdminCommand {

    private enum CommandEnum {
        LEVEL,
        SPEED,
        ATTACK,
        HP,
        SIZE
    }

    @Override
    public void execute(final TeraGameConnection connection, final String[] arguments) {
        final Player targetPlayer = WorldService.getInstance().getOnlinePlayerByName(arguments[1]);
        if (targetPlayer == null) {
            this.sendTranslatedErrorMessage(connection, AdminErrorMessageEnum.TARGET_NOT_FOUND.key);
            return;
        }
        
        final Account targetAccount = targetPlayer.getAccount();
        if (targetAccount.getAccess() > connection.getAccount().getAccess()) {
            this.sendTranslatedErrorMessage(connection, AdminErrorMessageEnum.TARGET_HAS_MORE_RIGHTS_THAN_YOU.key);
            return;
        }
        
        final CommandEnum command = CommandEnum.valueOf(arguments[0].toUpperCase());
        switch (command) {
            case LEVEL:
                PlayerService.getInstance().levelUpPlayer(targetPlayer, Integer.parseInt(arguments[2]));
                connection.sendPacket(new SM_PLAYER_EXPERIENCE_UPDATE(connection.getActivePlayer(), null, 0));
            break;
            
            case SPEED:
                targetPlayer.getCurrentStats().setSpeed(Integer.parseInt(arguments[2]));
                targetPlayer.getConnection().sendPacket(new SM_PLAYER_STATS_UPDATE(targetPlayer));
            break;
            
            case ATTACK:
                targetPlayer.getTemplate().getBaseStats().setAttack(Integer.parseInt(arguments[2]));
                targetPlayer.getConnection().sendPacket(new SM_PLAYER_STATS_UPDATE(targetPlayer));
            break;
            
            case HP:
                targetPlayer.getCurrentStats().setHp(Integer.parseInt(arguments[2]));
            break;
            
            case SIZE:
                targetPlayer.getPlayerCustomData().setSize(Float.parseFloat(arguments[2]));
                targetPlayer.getConnection().sendPacket(new SM_LOAD_TOPO(targetPlayer.getWorldPosition()));
            break;
        }
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
            CommandEnum.valueOf(arguments[0].toUpperCase());
            Double.parseDouble(arguments[2]);
            return true;
        } catch (final Exception e) {
            return false;
        }
    }

    @Override
    public String getSyntax() {
        return "set {level|speed|attack|hp} [targetName] [value]";
    }
}
