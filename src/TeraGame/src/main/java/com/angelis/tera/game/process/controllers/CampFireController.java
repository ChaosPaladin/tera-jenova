package com.angelis.tera.game.process.controllers;

import com.angelis.tera.game.presentation.network.SystemMessages;
import com.angelis.tera.game.process.model.TeraObject;
import com.angelis.tera.game.process.model.campfire.CampFire;
import com.angelis.tera.game.process.model.creature.VisibleObjectEventTypeEnum;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.model.visible.VisibleTeraObject;

public class CampFireController extends Controller<CampFire> {

    private static final int MAX_DISTANCE = 100;

    @Override
    public void update(final VisibleObjectEventTypeEnum creatureEventType, final TeraObject object, final Object... data) {

        switch (creatureEventType) {
            case APPEAR:
            case CREATURE_MOVE:
                if (object instanceof Player) {
                    final Player player = (Player) object;

                    if (player.getActiveCampFire() != null) {
                        final double distance = player.getWorldPosition().distanceTo(player.getActiveCampFire().getWorldPosition());
                        if (distance <= MAX_DISTANCE) {
                            return;
                        }

                        player.setActiveCampFire(null);
                        player.getConnection().sendPacket(SystemMessages.YOU_ARE_NO_LONGER_CHARGING_STAMINA());
                    }

                    if (player.getActiveCampFire() == null) {
                        final double distance = player.getWorldPosition().distanceTo(this.owner.getWorldPosition());
                        if (distance <= MAX_DISTANCE) {
                            player.setActiveCampFire(this.owner);
                            player.getConnection().sendPacket(SystemMessages.YOU_ARE_CHARGING_STAMINA());
                            break;
                        }
                    }
                }
            break;

            default:
        }
    }

    @Override
    public void onStartAttack(final VisibleTeraObject target) {
        // Nothing to do
    }

    @Override
    public void onDamage(final VisibleTeraObject attacker, final int damage) {
        // Nothing to do
    }

    @Override
    public void onEndAttack() {
        // Nothing to do
    }

    @Override
    public void onDie(final VisibleTeraObject killer) {
        // Nothing to do
    }

    @Override
    public void onRespawn() {
        // Nothing to do
    }
}
