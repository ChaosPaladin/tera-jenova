package com.angelis.tera.game.controllers;

import com.angelis.tera.game.process.model.TeraObject;
import com.angelis.tera.game.process.model.creature.VisibleObjectEventTypeEnum;
import com.angelis.tera.game.process.model.gameobject.GameObject;
import com.angelis.tera.game.process.model.visible.VisibleTeraObject;

public class GameObjectController extends Controller<GameObject> {

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
        // TODO Auto-generated method stub
        
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
