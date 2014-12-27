package com.angelis.tera.game.ai.state.enums;

public enum AIStateEnum {
    THINKING(5),
    TALKING(4),
    AGGRO(3),
    ACTIVE(3),
    USESKILL(3),
    ATTACKING(2),
    RESTING(1),
    MOVINGTOHOME(1),
    NONE(0),
    ;

    public final int priority;

    private AIStateEnum(final int priority) {
        this.priority = priority;
    }
}
