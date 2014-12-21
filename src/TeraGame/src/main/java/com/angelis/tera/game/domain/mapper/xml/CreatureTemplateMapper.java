package com.angelis.tera.game.domain.mapper.xml;

import com.angelis.tera.common.domain.mapper.MapperManager;
import com.angelis.tera.common.domain.mapper.xml.AbstractXMLMapper;
import com.angelis.tera.game.process.model.template.CreatureTemplate;
import com.angelis.tera.game.xml.entity.creatures.template.CreatureTemplateEntity;

public class CreatureTemplateMapper extends AbstractXMLMapper<CreatureTemplateEntity, CreatureTemplate> {

    // MODEL -> ENTITY
    @Override
    protected CreatureTemplateEntity createNewEmptyEntity() {
        return new CreatureTemplateEntity();
    }

    @Override
    public void map(final CreatureTemplate model, final CreatureTemplateEntity entity) {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void finalizeDependencies(final CreatureTemplate model, final CreatureTemplateEntity entity) {
        // TODO Auto-generated method stub
        
    }

    // ENTITY -> MODEL
    @Override
    protected CreatureTemplate createNewEmptyModel() {
        return new CreatureTemplate();
    }

    @Override
    public void map(final CreatureTemplateEntity entity, final CreatureTemplate model) {
        // DIRECT
        model.setFullId(entity.getFullId());
        model.setRank(entity.getRank());
        model.setExp(entity.getExp());
        model.setHuntingZoneId(entity.getHuntingZoneId());
        model.setModelId(entity.getModelId());
        model.setOffensive(entity.isOffensive());
        model.setCreatureTitle(entity.getCreatureTitle());
    }

    @Override
    protected void finalizeDependencies(final CreatureTemplateEntity entity, final CreatureTemplate model) {
        model.setBaseStats(MapperManager.getXMLMapper(BaseStatsMapper.class).map(entity.getBaseStats()));
    }
}
