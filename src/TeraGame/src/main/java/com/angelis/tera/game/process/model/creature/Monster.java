package com.angelis.tera.game.process.model.creature;

import com.angelis.tera.game.ai.impl.MonsterAI;

public class Monster extends Creature {

    public Monster(final Integer id) {
        super(id);
    }

    public Monster(final Monster monster) {
        super(monster);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof Monster)) {
            return false;
        }
        
        return true;
    }

    @Override
    public void initializeAi() {
        this.ai = new MonsterAI(this);
    }
}
