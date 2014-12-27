package com.angelis.tera.game.ai.events.handlers;

import com.angelis.tera.game.ai.AI;
import com.angelis.tera.game.ai.events.AIEventEnum;
import com.angelis.tera.game.ai.events.IAIEventHandler;

public class NotSeePlayerEventHandler implements IAIEventHandler {

    @Override
    public AIEventEnum getEvent() {
        return AIEventEnum.NOT_SEE_PLAYER;
    }

    @Override
    public void handleEvent(final AIEventEnum event, final AI<?> ai) {
        // TODO Auto-generated method stub
        
    }

}
