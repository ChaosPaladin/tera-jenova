package com.angelis.tera.game.tasks.pegasus;

import com.angelis.tera.game.process.model.pegasus.PegasusFly;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.services.PegasusFlyService;
import com.angelis.tera.game.tasks.AbstractTask;
import com.angelis.tera.game.tasks.TaskTypeEnum;

public class StartPegasusFlyTask extends AbstractTask<Player> {

    private long start = System.currentTimeMillis();
    private int tick;
    
    public StartPegasusFlyTask(final Player linkedObject) {
        super(linkedObject, TaskTypeEnum.PEGASUS_FLY_START);
    }

    @Override
    public void execute() {
        final PegasusFly pegasus = this.linkedObject.getActivePegasus();
        
        tick++;
        if (tick == pegasus.getChangeMapTickCount()) {
            this.start = System.currentTimeMillis();
            PegasusFlyService.getInstance().onPlayerHalfPegasusFly(this.linkedObject);
            this.cancel();
            return;
        }
        
        final int elapsedTime = (int) (System.currentTimeMillis() - this.start);
        PegasusFlyService.getInstance().onPlayerRunPegasusFly(this.linkedObject, 0, elapsedTime);
    }
}