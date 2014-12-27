package com.angelis.tera.game.process.tasks.campfire;

import com.angelis.tera.game.process.model.campfire.CampFire;
import com.angelis.tera.game.process.services.SpawnService;
import com.angelis.tera.game.process.tasks.AbstractTask;
import com.angelis.tera.game.process.tasks.TaskTypeEnum;

public class CampFireDespawnTask extends AbstractTask<CampFire> {

    public CampFireDespawnTask(final CampFire linkedObject) {
        super(linkedObject, TaskTypeEnum.TERA_OBJECT_DESPAWN);
    }

    @Override
    public void execute() {
        SpawnService.getInstance().despawnCampFire(this.linkedObject);
    }

}
