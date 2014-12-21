package com.angelis.tera.game.tasks.player;

import com.angelis.tera.game.process.delegate.database.PlayerDelegate;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.tasks.AbstractTask;
import com.angelis.tera.game.tasks.TaskTypeEnum;

public class PlayerAutoSaveTask extends AbstractTask<Player> {

    public PlayerAutoSaveTask(final Player linkedObject) {
        super(linkedObject, TaskTypeEnum.PLAYER_AUTO_SAVE);
    }

    @Override
    public void execute() {
        new PlayerDelegate().update(this.linkedObject);
    }
}
