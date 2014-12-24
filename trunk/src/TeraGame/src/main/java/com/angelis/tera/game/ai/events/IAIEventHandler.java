package com.angelis.tera.game.ai.events;

import com.angelis.tera.game.ai.AI;

public interface IAIEventHandler {
    AIEventEnum getEvent();
    void handleEvent(AIEventEnum event, AI<?> ai);
}
