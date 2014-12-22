package com.angelis.tera.game.ai.events.handlers;

import com.angelis.tera.game.ai.AI;
import com.angelis.tera.game.ai.events.Event;
import com.angelis.tera.game.ai.events.IEventHandler;
import com.angelis.tera.game.ai.state.enums.AIStateEnum;

public class AttackedEventHandler implements IEventHandler {

    @Override
    public Event getEvent() {
        return Event.ATTACKED;
    }

    @Override
    public void handleEvent(final Event event, final AI<?> ai) {
        ai.setAiState(AIStateEnum.ATTACKING);
        if (!ai.isScheduled()) {
            ai.analyzeState();
        }
    }

}
