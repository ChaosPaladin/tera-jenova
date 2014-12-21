package com.angelis.tera.game.domain.mapper.xml;

import com.angelis.tera.common.domain.mapper.xml.AbstractXMLMapper;
import com.angelis.tera.game.process.model.template.GatherTemplate;
import com.angelis.tera.game.xml.entity.gathers.GatherTemplateEntity;

public class GatherTemplateMapper extends AbstractXMLMapper<GatherTemplateEntity, GatherTemplate> {

    @Override
    protected GatherTemplateEntity createNewEmptyEntity() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void map(final GatherTemplate model, final GatherTemplateEntity entity) {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void finalizeDependencies(final GatherTemplate model, final GatherTemplateEntity entity) {
        // No dependency
    }

    @Override
    protected GatherTemplate createNewEmptyModel() {
        return new GatherTemplate();
    }

    @Override
    public void map(final GatherTemplateEntity entity, final GatherTemplate model) {
        // DIRECT
        model.setGatherType(entity.getGatherType());
        model.setMinLevel(entity.getMinLevel());
        model.setMaxLevel(entity.getMaxLevel());
        model.setGatherCostPoint(entity.getGatherCostPoint());
    }

    @Override
    protected void finalizeDependencies(final GatherTemplateEntity entity, final GatherTemplate model) {
        // No dependency
    }
}
