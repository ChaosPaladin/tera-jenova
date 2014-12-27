package com.angelis.tera.game.process.ai.state;

import com.angelis.tera.game.process.ai.AI;
import com.angelis.tera.game.process.ai.state.enums.AIStateEnum;

public interface IStateHandler {
    AIStateEnum getState();
    void handleState(AIStateEnum state, AI<?> ai);
}
