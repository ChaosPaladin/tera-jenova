package com.angelis.tera.game.domain.mapper.xml;

import com.angelis.tera.common.domain.mapper.xml.AbstractXMLMapper;
import com.angelis.tera.game.domain.entity.xml.DropEntity;
import com.angelis.tera.game.process.model.drop.Drop;

public class DropMapper extends AbstractXMLMapper<DropEntity, Drop> {

    // ENTITY -> MODEL
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
        // No dependency
    }

}
