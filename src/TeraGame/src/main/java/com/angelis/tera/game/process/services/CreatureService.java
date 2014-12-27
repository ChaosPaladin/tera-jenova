package com.angelis.tera.game.process.services;

import java.util.List;

import javolution.util.FastList;

import org.apache.log4j.Logger;

import com.angelis.tera.common.services.AbstractService;
import com.angelis.tera.game.process.model.creature.AbstractCreature;
import com.angelis.tera.game.process.model.creature.VisibleObjectEventTypeEnum;
import com.angelis.tera.game.process.model.visible.WorldPosition;
import com.angelis.tera.game.process.model.visible.enums.VisibleTypeEnum;
import com.angelis.tera.game.utils.Geom;

public class CreatureService extends AbstractService {

    /** LOGGER */
    private static Logger log = Logger.getLogger(CreatureService.class.getName());

    private final List<Long> creaturesMoney = new FastList<>();

    @Override
    public void onInit() {
        final double x1 = 1;
        final double y1 = 131;
        final double x2 = 5;
        final double y2 = 207;
        final double x3 = 36;
        final double y3 = 2854;

        final double a = (y3 - ((x3 * (y2 - y1) + x2 * y1 - x1 * y2) / (x2 - x1))) / (x3 * (x3 - x1 - x2) + x1 * x2);
        final double b = (y2 - y1) / (x2 - x1) - a * (x1 + x2);
        final double c = ((x2 * y1 - x1 * y2) / (x2 - x1)) + a * x1 * x2;

        for (int i = 0; i < 100; i++) {
            creaturesMoney.add((long) (a * i * i + b * i + c));
        }

        log.info("CreatureService started");
    }

    @Override
    public void onDestroy() {
        log.info("CreatureService stopped");
    }

    public void moveCreature(final AbstractCreature creature, final WorldPosition newWorldPosition) {
        final WorldPosition worldPosition = creature.getWorldPosition();
        final short heading = Geom.getHeading(worldPosition, newWorldPosition);
        creature.getKnownList().notify(VisibleTypeEnum.PLAYER, VisibleObjectEventTypeEnum.CREATURE_MOVE, newWorldPosition.getX(), newWorldPosition.getY(), newWorldPosition.getZ(), heading, worldPosition.getX(), worldPosition.getY(), worldPosition.getZ());

        worldPosition.setPoint3D(newWorldPosition.getPoint3D());
        worldPosition.setHeading(heading);
    }

    public void killCreature(final AbstractCreature creature, final AbstractCreature killer) {
        creature.getCurrentStats().setHp(0);
        creature.getController().onDie(killer);
    }

    public long getMoney(final int level) {
        if (!this.creaturesMoney.contains(level)) {
            return 0;
        }

        return this.creaturesMoney.get(level);
    }

    /** SINGLETON */
    public static CreatureService getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        protected static final CreatureService instance = new CreatureService();
    }
}
