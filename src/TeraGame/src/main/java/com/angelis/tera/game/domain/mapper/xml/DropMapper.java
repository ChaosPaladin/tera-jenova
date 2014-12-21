package com.angelis.tera.game.domain.mapper.xml;

import com.angelis.tera.common.domain.mapper.xml.AbstractXMLMapper;
import com.angelis.tera.game.process.model.drop.Drop;
import com.angelis.tera.game.xml.entity.DropEntity;

public class DropMapper extends AbstractXMLMapper<DropEntity, Drop> {

    @Override
    protected DropEntity createNewEmptyEntity() {
        return new DropEntity();
    }

    @Override
    public void map(final Drop model, final DropEntity entity) {
        entity.setItemCategory(model.getItemCategory());
        entity.setDropChance(model.getDropChance());
        entity.setItemId(model.getItemId());
        entity.setMaxAmount(model.getMaxAmount());
        entity.setMinAmount(model.getMinAmount());
    }

    @Override
    protected void finalizeDependencies(final Drop model, final DropEntity entity) {
        // No dependency
    }

    @Override
    protected Drop createNewEmptyModel() {
        return new Drop();
    }

    @Override
    public void map(final DropEntity entity, final Drop model) {
        model.setItemCategory(entity.getItemCategory());
        model.setDropChance(entity.getDropChance());
        model.setItemId(entity.getItemId());
        model.setMaxAmount(entity.getMaxAmount());
        model.setMinAmount(entity.getMinAmount());
    }

    @Override
    protected void finalizeDependencies(final DropEntity entity, final Drop model) {
        // TODO Auto-generated method stub
        
    }

    

}
