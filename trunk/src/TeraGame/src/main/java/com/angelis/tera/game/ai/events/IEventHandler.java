package com.angelis.tera.game.ai.events;

import com.angelis.tera.game.ai.AI;

public interface IEventHandler {
    
    Event getEvent();
    void handleEvent(Event event, AI<?> ai);
}
