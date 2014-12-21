package com.angelis.tera.game.controllers;

import com.angelis.tera.game.process.model.TeraObject;
import com.angelis.tera.game.process.model.creature.VisibleObjectEventTypeEnum;
import com.angelis.tera.game.process.model.visible.VisibleTeraObject;

public abstract class Controller<O extends TeraObject> {
    
    protected O owner;

    public Controller() {}

    public O getOwner() {
        return owner;
    }

    public void setOwner(final O owner) {
        this.owner = owner;
    }

    public abstract void update(VisibleObjectEventTypeEnum creatureEventType, TeraObject object, Object... data);
    
    public abstract void onStartAttack(VisibleTeraObject target);
    public abstract void onDamage(final VisibleTeraObject attacker, final int damage);
    public abstract void onEndAttack();
    public abstract void onDie(VisibleTeraObject killer);
    public abstract void onRespawn();
}
