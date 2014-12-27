package com.angelis.tera.game.process.ai.events;

import com.angelis.tera.game.process.ai.AI;

public interface IAIEventHandler {
    AIEventEnum getEvent();
    void handleEvent(AIEventEnum event, AI<?> ai);
}
