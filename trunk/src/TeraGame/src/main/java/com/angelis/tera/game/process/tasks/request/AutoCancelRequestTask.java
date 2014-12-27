package com.angelis.tera.game.process.tasks.request;

import com.angelis.tera.game.process.model.player.request.AbstractRequest;
import com.angelis.tera.game.process.tasks.AbstractTask;
import com.angelis.tera.game.process.tasks.TaskTypeEnum;

public class AutoCancelRequestTask extends AbstractTask<AbstractRequest<?, ?>> {

    public AutoCancelRequestTask(final AbstractRequest<?, ?> linkedObject) {
        super(linkedObject, TaskTypeEnum.REQUEST_AUTO_CANCEL);
    }

    @Override
    public void execute() {
        this.linkedObject.doCancel();
    }
}
