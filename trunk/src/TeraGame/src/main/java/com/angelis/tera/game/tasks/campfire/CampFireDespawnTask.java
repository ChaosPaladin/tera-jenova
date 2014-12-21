package com.angelis.tera.game.tasks.campfire;

import com.angelis.tera.game.process.model.campfire.CampFire;
import com.angelis.tera.game.services.SpawnService;
import com.angelis.tera.game.tasks.AbstractTask;
import com.angelis.tera.game.tasks.TaskTypeEnum;

public class CampFireDespawnTask extends AbstractTask<CampFire> {

    public CampFireDespawnTask(final CampFire linkedObject) {
        super(linkedObject, TaskTypeEnum.TERA_OBJECT_DESPAWN);
    }

    @Override
    public void execute() {
        SpawnService.getInstance().despawnCampFire(this.linkedObject);
    }

}
