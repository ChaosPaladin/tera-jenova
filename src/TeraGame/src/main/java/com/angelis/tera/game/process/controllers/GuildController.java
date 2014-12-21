package com.angelis.tera.game.process.controllers;

import com.angelis.tera.game.process.model.TeraObject;
import com.angelis.tera.game.process.model.creature.VisibleObjectEventTypeEnum;
import com.angelis.tera.game.process.model.guild.Guild;
import com.angelis.tera.game.process.model.visible.VisibleTeraObject;

public class GuildController extends Controller<Guild> {

    @Override
    public void update(final VisibleObjectEventTypeEnum creatureEventType, final TeraObject object, final Object... data) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStartAttack(final VisibleTeraObject target) {
    }

    @Override
    public void onDamage(final VisibleTeraObject attacker, final int damage) {
        // Nothing to do
    }

    @Override
    public void onEndAttack() {
    }

    @Override
    public void onDie(final VisibleTeraObject killer) {
    }

    @Override
    public void onRespawn() {
    }

}
