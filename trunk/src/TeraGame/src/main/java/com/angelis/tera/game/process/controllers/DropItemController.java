package com.angelis.tera.game.process.controllers;

import com.angelis.tera.game.process.model.TeraObject;
import com.angelis.tera.game.process.model.creature.VisibleObjectEventTypeEnum;
import com.angelis.tera.game.process.model.drop.DropItem;
import com.angelis.tera.game.process.model.visible.VisibleTeraObject;

public class DropItemController extends Controller<DropItem> {

    @Override
    public void update(final VisibleObjectEventTypeEnum creatureEventType, final TeraObject object, final Object... data) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onStartAttack(final VisibleTeraObject target) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onDamage(final VisibleTeraObject attacker, final int damage) {
        
    }
    
    @Override
    public void onEndAttack() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onDie(final VisibleTeraObject killer) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onRespawn() {
        // TODO Auto-generated method stub
        
    }
}
