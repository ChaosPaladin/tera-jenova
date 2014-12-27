package com.angelis.tera.game.ai.state;

import com.angelis.tera.game.ai.AI;
import com.angelis.tera.game.ai.state.enums.AIStateEnum;

public interface IStateHandler {
    AIStateEnum getState();
    void handleState(AIStateEnum state, AI<?> ai);
}
