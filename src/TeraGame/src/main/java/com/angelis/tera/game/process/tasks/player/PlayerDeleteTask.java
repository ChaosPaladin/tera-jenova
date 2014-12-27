package com.angelis.tera.game.process.tasks.player;

import com.angelis.tera.game.process.delegate.database.PlayerDelegate;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.tasks.AbstractTask;
import com.angelis.tera.game.process.tasks.TaskTypeEnum;

public class PlayerDeleteTask extends AbstractTask<Player> {

    public PlayerDeleteTask(final Player linkedObject) {
        super(linkedObject, TaskTypeEnum.PLAYER_DELETE);
    }

    @Override
    public void execute() {
        new PlayerDelegate().delete(this.linkedObject);
    }
}
