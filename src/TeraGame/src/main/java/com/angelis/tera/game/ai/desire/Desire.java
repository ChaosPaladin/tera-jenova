package com.angelis.tera.game.ai.desire;

public abstract class Desire {

    private int desirePower;
    
    public int getDesirePower() {
        return desirePower;
    }

    public void increaseDesirePower(final int desirePower) {
        this.desirePower += desirePower;
    }

}
