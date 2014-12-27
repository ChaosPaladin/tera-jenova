package com.angelis.tera.game.process.tasks.gather;

import com.angelis.tera.game.process.model.gather.Gather;
import com.angelis.tera.game.process.tasks.AbstractTask;
import com.angelis.tera.game.process.tasks.TaskTypeEnum;

public class GatherRespawnTask extends AbstractTask<Gather> {

    public GatherRespawnTask(final Gather linkedObject) {
        super(linkedObject, TaskTypeEnum.TERA_OBJECT_RESPAWN);
    }

    @Override
    public void execute() {
        this.getLinkedObject().getController().onRespawn();
    }
}
