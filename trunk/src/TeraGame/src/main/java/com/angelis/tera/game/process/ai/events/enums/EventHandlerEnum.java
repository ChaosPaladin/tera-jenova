package com.angelis.tera.game.process.ai.events.enums;

import com.angelis.tera.game.process.ai.events.IAIEventHandler;
import com.angelis.tera.game.process.ai.events.handlers.AttackedEventHandler;
import com.angelis.tera.game.process.ai.events.handlers.NotSeePlayerEventHandler;
import com.angelis.tera.game.process.ai.events.handlers.SeePlayerEventHandler;

public enum EventHandlerEnum {
    SEE_PLAYER(new SeePlayerEventHandler()),
    NOT_SEE_PLAYER(new NotSeePlayerEventHandler()),

    ATTACKED(new AttackedEventHandler());
    
    public final IAIEventHandler eventHandler;

    private EventHandlerEnum(final IAIEventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }
}
