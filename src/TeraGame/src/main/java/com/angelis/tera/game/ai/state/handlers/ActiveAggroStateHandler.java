package com.angelis.tera.game.ai.state.handlers;

import com.angelis.tera.game.ai.AI;
import com.angelis.tera.game.ai.state.IStateHandler;
import com.angelis.tera.game.ai.state.enums.AIStateEnum;

public class ActiveAggroStateHandler implements IStateHandler {

    @Override
    public AIStateEnum getState() {
        return AIStateEnum.AGGRO;
    }

    @Override
    public void handleState(final AIStateEnum state, final AI<?> ai) {
        // TODO Auto-generated method stub
        
    }

}
