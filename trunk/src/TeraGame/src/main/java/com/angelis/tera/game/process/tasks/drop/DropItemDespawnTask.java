package com.angelis.tera.game.process.tasks.drop;

import com.angelis.tera.game.process.model.drop.DropItem;
import com.angelis.tera.game.process.services.SpawnService;
import com.angelis.tera.game.process.tasks.AbstractTask;
import com.angelis.tera.game.process.tasks.TaskTypeEnum;

public class DropItemDespawnTask extends AbstractTask<DropItem> {

    public DropItemDespawnTask(final DropItem linkedObject) {
        super(linkedObject, TaskTypeEnum.ITEM_FREE_LOOT);
    }

    @Override
    public void execute() {
        SpawnService.getInstance().despawnDropItem(this.linkedObject);
    }
}
