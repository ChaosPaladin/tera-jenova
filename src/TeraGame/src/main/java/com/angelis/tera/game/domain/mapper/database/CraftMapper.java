package com.angelis.tera.game.domain.mapper.database;

import java.util.List;

import com.angelis.tera.common.domain.entity.database.AbstractDatabaseEntity;
import com.angelis.tera.common.domain.mapper.database.AbstractDatabaseMapper;
import com.angelis.tera.common.process.model.AbstractModel;
import com.angelis.tera.common.utils.BeanUtils;
import com.angelis.tera.game.domain.entity.database.CraftEntity;
import com.angelis.tera.game.process.model.player.craft.Craft;

public class CraftMapper extends AbstractDatabaseMapper<CraftEntity, Craft> {

    @Override
    protected CraftEntity createNewEmptyEntity() {
        return new CraftEntity();
    }

    @Override
    public void map(final Craft model, final CraftEntity entity) {
        entity.setCraftType(model.getCraftType());
        entity.setLevel(model.getLevel());
    }

    @Override
    protected void finalizeDependencies(final Craft model, final CraftEntity entity, final List<Class<? extends AbstractModel>> excludedDependencies) {
        // No dependency
    }

    @Override
    protected Craft createNewEmptyModel() {
        return new Craft();
    }

    @Override
    public void map(final CraftEntity entity, final Craft model) {
        model.setCraftType(entity.getCraftType());
        model.setLevel(entity.getLevel());
    }

    @Override
    protected void finalizeDependencies(final CraftEntity entity, final Craft model, final List<Class<? extends AbstractDatabaseEntity>> excludedDependencies) {
        // No dependency
    }

    @Override
    public void merge(final CraftEntity existingEntity, final CraftEntity currentEntity) {
        BeanUtils.fill(CraftEntity.class, existingEntity, currentEntity);
    }
}
