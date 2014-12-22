package com.angelis.tera.game.ai.events.handlers;

import com.angelis.tera.game.ai.AI;
import com.angelis.tera.game.ai.events.Event;
import com.angelis.tera.game.ai.events.IEventHandler;

public class NotSeePlayerEventHandler implements IEventHandler {

    @Override
    public Event getEvent() {
        return Event.NOT_SEE_PLAYER;
    }

    @Override
    public void handleEvent(final Event event, final AI<?> ai) {
        // TODO Auto-generated method stub
        
    }

}
