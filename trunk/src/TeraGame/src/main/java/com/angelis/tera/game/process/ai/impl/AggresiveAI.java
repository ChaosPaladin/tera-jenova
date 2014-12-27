package com.angelis.tera.game.process.ai.impl;

import com.angelis.tera.game.process.ai.events.enums.EventHandlerEnum;
import com.angelis.tera.game.process.ai.state.enums.StateHandlerEnum;
import com.angelis.tera.game.process.model.creature.Monster;

public class AggresiveAI extends MonsterAI {

    public AggresiveAI(final Monster visibleTeraObject) {
        super(visibleTeraObject);
        
        /**
         * Event handlers
         */
        this.addEventHandler(EventHandlerEnum.SEE_PLAYER.eventHandler);
        this.addEventHandler(EventHandlerEnum.NOT_SEE_PLAYER.eventHandler);

        /**
         * State handlers
         */
        this.addStateHandler(StateHandlerEnum.ACTIVE_AGGRO.stateHandler);
    }
}
