package com.angelis.tera.game.domain.mapper.xml;

import com.angelis.tera.common.domain.mapper.MapperManager;
import com.angelis.tera.common.domain.mapper.xml.AbstractXMLMapper;
import com.angelis.tera.game.domain.entity.xml.creatures.template.CreatureTemplateEntity;
import com.angelis.tera.game.process.model.template.CreatureTemplate;

public class CreatureTemplateMapper extends AbstractXMLMapper<CreatureTemplateEntity, CreatureTemplate> {

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
        final BaseStatsMapper baseStatsMapper = MapperManager.getXMLMapper(BaseStatsMapper.class);
        model.setBaseStats(baseStatsMapper.map(entity.getBaseStats()));
    }
}
