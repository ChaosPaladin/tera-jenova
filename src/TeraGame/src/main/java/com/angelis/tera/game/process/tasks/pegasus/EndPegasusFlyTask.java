package com.angelis.tera.game.process.tasks.pegasus;

import com.angelis.tera.game.process.model.pegasus.PegasusFly;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.services.PegasusFlyService;
import com.angelis.tera.game.process.tasks.AbstractTask;
import com.angelis.tera.game.process.tasks.TaskTypeEnum;

public class EndPegasusFlyTask extends AbstractTask<Player> {

    private final long start = System.currentTimeMillis();
    private int tick;
    
    public EndPegasusFlyTask(final Player linkedObject) {
        super(linkedObject, TaskTypeEnum.PEGASUS_FLY_END);
    }

    @Override
    public void execute() {
        final PegasusFly pegasus = this.linkedObject.getActivePegasus();
        
        tick++;
        if (tick == pegasus.getEndFlyTickCount()) {
            PegasusFlyService.getInstance().onPlayerEndPegasusFly(this.linkedObject);
            this.cancel();
            return;
        }
        
        final int elapsedTime = (int) (System.currentTimeMillis() - this.start);
        PegasusFlyService.getInstance().onPlayerRunPegasusFly(this.linkedObject, 1, elapsedTime);
    }
}