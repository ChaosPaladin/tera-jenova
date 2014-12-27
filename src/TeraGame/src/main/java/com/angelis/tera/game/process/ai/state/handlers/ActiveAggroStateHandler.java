package com.angelis.tera.game.process.ai.state.handlers;

import com.angelis.tera.game.process.ai.AI;
import com.angelis.tera.game.process.ai.state.IStateHandler;
import com.angelis.tera.game.process.ai.state.enums.AIStateEnum;

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
