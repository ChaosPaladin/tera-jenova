package com.angelis.tera.game.process.tasks.creature;

import com.angelis.tera.game.process.model.creature.AbstractCreature;
import com.angelis.tera.game.process.tasks.AbstractTask;
import com.angelis.tera.game.process.tasks.TaskTypeEnum;

public class CreatureRespawnTask extends AbstractTask<AbstractCreature> {

    public CreatureRespawnTask(final AbstractCreature linkedObject) {
        super(linkedObject, TaskTypeEnum.TERA_OBJECT_RESPAWN);
    }

    @Override
    public void execute() {
        
    }
}
