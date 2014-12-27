package com.angelis.tera.game.process.ai.events.handlers;

import com.angelis.tera.game.process.ai.AI;
import com.angelis.tera.game.process.ai.events.AIEventEnum;
import com.angelis.tera.game.process.ai.events.IAIEventHandler;
import com.angelis.tera.game.process.ai.state.enums.AIStateEnum;

public class AttackedEventHandler implements IAIEventHandler {

    @Override
    public AIEventEnum getEvent() {
        return AIEventEnum.ATTACKED;
    }

    @Override
    public void handleEvent(final AIEventEnum event, final AI<?> ai) {
        ai.setAiState(AIStateEnum.ATTACKING);
        if (!ai.isScheduled()) {
            ai.analyzeState();
        }
    }

}
