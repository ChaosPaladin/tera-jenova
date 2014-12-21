package com.angelis.tera.game.process.model.creature;

import com.angelis.tera.game.process.controllers.CreatureController;
import com.angelis.tera.game.process.model.template.CreatureTemplate;

public abstract class Creature extends AbstractCreature {

    private final AggroList aggroList = new AggroList();
    
    public Creature(final Integer id) {
        super(id, new CreatureController(), new CreatureTemplate());
        this.getController().setOwner(this);
    }
    
    public Creature(final Creature teraCreature) {
        super(teraCreature, new CreatureController());
        this.getController().setOwner(this);
    }

    @Override
    public CreatureController getController() {
        return (CreatureController) this.controller;
    }

    @Override
    public CreatureTemplate getTemplate() {
        return (CreatureTemplate) this.template;
    }
    
    public AggroList getAggroList() {
        return aggroList;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof Creature)) {
            return false;
        }
        
        return true;
    }
}
