package com.angelis.tera.game.process.tasks.player;

import java.util.List;

import com.angelis.tera.game.process.model.creature.Creature;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.services.BattleService;
import com.angelis.tera.game.process.tasks.AbstractTask;
import com.angelis.tera.game.process.tasks.TaskTypeEnum;

public class PlayerCheckAggro extends AbstractTask<Player> {

    public PlayerCheckAggro(final Player linkedObject, final TaskTypeEnum taskType) {
        super(linkedObject, TaskTypeEnum.PLAYER_CHECK_AGGRO);
    }

    @Override
    public void execute() {
        final List<Creature> teraCreatures = BattleService.getInstance().getCreaturesAggroListContainingVisibleTeraObject(this.linkedObject);
        if (teraCreatures.isEmpty()) {
            this.cancel();
            this.linkedObject.getController().onEndAttack();
        }
    }
}
