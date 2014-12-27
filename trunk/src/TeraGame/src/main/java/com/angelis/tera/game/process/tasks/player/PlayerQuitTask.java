package com.angelis.tera.game.process.tasks.player;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.server.SM_QUIT_GAME;
import com.angelis.tera.game.process.model.account.Account;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.services.PlayerService;
import com.angelis.tera.game.process.tasks.AbstractTask;
import com.angelis.tera.game.process.tasks.TaskTypeEnum;

public class PlayerQuitTask extends AbstractTask<Player> {

    public PlayerQuitTask(final Player linkedObject) {
        super(linkedObject, TaskTypeEnum.PLAYER_QUIT);
    }

    @Override
    public void execute() {
        final Player player = this.linkedObject;
        if (player == null) {
            return;
        }
        
        final Account account = player.getAccount();
        if (account == null) {
            return;
        }
        
        final TeraGameConnection connection = account.getConnection();
        if (connection == null) {
            return;
        }
        
        connection.sendPacket(new SM_QUIT_GAME());
        PlayerService.getInstance().onPlayerDisconnect(player);
    }
}
