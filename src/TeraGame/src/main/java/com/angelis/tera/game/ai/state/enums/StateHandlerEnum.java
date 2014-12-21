package com.angelis.tera.game.ai.state.enums;

import com.angelis.tera.game.ai.state.IStateHandler;
import com.angelis.tera.game.ai.state.handlers.ActiveAggroStateHandler;
import com.angelis.tera.game.ai.state.handlers.AttackingStateHandler;

public enum StateHandlerEnum {
    ATTACKING(new AttackingStateHandler()),
    ACTIVE_AGGRO(new ActiveAggroStateHandler()),
    ;

    public final IStateHandler stateHandler;

    private StateHandlerEnum(final IStateHandler stateHandler) {
        this.stateHandler = stateHandler;
    }
}
