package com.angelis.tera.game.domain.mapper.xml;

import com.angelis.tera.common.domain.mapper.xml.AbstractXMLMapper;
import com.angelis.tera.game.process.model.creature.Creature;
import com.angelis.tera.game.xml.entity.creatures.spawn.CreatureSpawnsEntity;

public class CreatureSpawnMapper extends AbstractXMLMapper<CreatureSpawnsEntity, Creature> {

    @Override
    protected CreatureSpawnsEntity createNewEmptyEntity() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void map(final Creature model, final CreatureSpawnsEntity entity) {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void finalizeDependencies(final Creature model, final CreatureSpawnsEntity entity) {
        // TODO Auto-generated method stub
        
    }

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
        // TODO Auto-generated method stub
        
    }
}
