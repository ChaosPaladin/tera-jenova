package com.angelis.tera.game.tasks.drop;

import com.angelis.tera.game.process.model.drop.DropItem;
import com.angelis.tera.game.services.SpawnService;
import com.angelis.tera.game.tasks.AbstractTask;
import com.angelis.tera.game.tasks.TaskTypeEnum;

public class DropItemDespawnTask extends AbstractTask<DropItem> {

    public DropItemDespawnTask(final DropItem linkedObject) {
        super(linkedObject, TaskTypeEnum.ITEM_FREE_LOOT);
    }

    @Override
    public void execute() {
        SpawnService.getInstance().despawnDropItem(this.linkedObject);
    }
}
