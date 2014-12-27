package com.angelis.tera.game.domain.mapper.xml;

import com.angelis.tera.common.domain.mapper.xml.AbstractXMLMapper;
import com.angelis.tera.game.domain.entity.xml.creatures.spawn.CreatureSpawnsEntity;
import com.angelis.tera.game.process.model.creature.Creature;

public class CreatureSpawnMapper extends AbstractXMLMapper<CreatureSpawnsEntity, Creature> {

    // ENTITY -> MODEL
    @Override
    protected Creature createNewEmptyModel() {
        return null;
    }

    @Override
    public void map(final CreatureSpawnsEntity entity, final Creature model) {
        // TODO Auto-generated method stub

    }

    @Override
    protected void finalizeDependencies(final CreatureSpawnsEntity entity, final Creature model) {
        // No dependency
    }
}
