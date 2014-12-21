package com.angelis.tera.game.tasks.drop;

import java.util.concurrent.TimeUnit;

import com.angelis.tera.game.config.DropConfig;
import com.angelis.tera.game.process.model.drop.DropItem;
import com.angelis.tera.game.services.ThreadPoolService;
import com.angelis.tera.game.tasks.AbstractTask;
import com.angelis.tera.game.tasks.TaskTypeEnum;

public class DropItemGetFreeTask extends AbstractTask<DropItem> {

    public DropItemGetFreeTask(final DropItem linkedObject) {
        super(linkedObject, TaskTypeEnum.ITEM_FREE_LOOT);
    }

    @Override
    public void execute() {
        this.linkedObject.setFree(true);
        ThreadPoolService.getInstance().scheduleTask(new DropItemDespawnTask(this.linkedObject), DropConfig.DROP_DESPAWN_TIME, TimeUnit.SECONDS);
    }
}
