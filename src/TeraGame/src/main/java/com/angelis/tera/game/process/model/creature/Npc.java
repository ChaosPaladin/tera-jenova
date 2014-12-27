package com.angelis.tera.game.process.model.creature;

import com.angelis.tera.game.ai.impl.NpcAI;

public class Npc extends Creature {

    public Npc(final Integer id) {
        super(id);
    }

    public Npc(final Npc npc) {
        super(npc);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof Npc)) {
            return false;
        }
        
        return true;
    }

    @Override
    public void initializeAi() {
        this.ai = new NpcAI(this);
    }
}
