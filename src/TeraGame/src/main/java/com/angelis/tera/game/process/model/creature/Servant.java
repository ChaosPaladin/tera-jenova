package com.angelis.tera.game.process.model.creature;

import com.angelis.tera.game.ai.impl.ServantAI;

public class Servant extends Creature {

    private AbstractCreature creator;

    public Servant(final Integer id) {
        super(id);
    }
    
    public Servant(final Integer id, final AbstractCreature creator) {
        this(id);
        this.creator = creator;
    }

    @Override
    public void initializeAi() {
        this.ai = new ServantAI(this);
    }
    
    public AbstractCreature getCreator() {
        return this.creator;
    }
}
