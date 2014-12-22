package com.angelis.tera.game.domain.mapper.database;

import java.util.List;

import com.angelis.tera.common.domain.entity.database.AbstractDatabaseEntity;
import com.angelis.tera.common.domain.mapper.database.AbstractDatabaseMapper;
import com.angelis.tera.common.process.model.AbstractModel;
import com.angelis.tera.common.utils.BeanUtils;
import com.angelis.tera.game.domain.entity.database.ZoneEntity;
import com.angelis.tera.game.process.model.Zone;

public class ZoneMapper extends AbstractDatabaseMapper<ZoneEntity, Zone> {

    @Override
    protected ZoneEntity createNewEmptyEntity() {
        return new ZoneEntity();
    }

    @Override
    public void map(final Zone model, final ZoneEntity entity) {
        entity.setDatas(model.getDatas());
    }

    @Override
    protected void finalizeDependencies(final Zone model, final ZoneEntity entity, final List<Class<? extends AbstractModel>> excludedDependencies) {
        // No dependency
    }

    @Override
    protected Zone createNewEmptyModel() {
        return new Zone();
    }

    @Override
    public void map(final ZoneEntity entity, final Zone model) {
        model.setDatas(entity.getDatas());
    }

    @Override
    protected void finalizeDependencies(final ZoneEntity entity, final Zone model, final List<Class<? extends AbstractDatabaseEntity>> excludedDependencies) {
        // No dependency
    }

    @Override
    public void merge(final ZoneEntity currentEntity, final ZoneEntity existingEntity) {
        BeanUtils.fill(ZoneEntity.class, currentEntity, existingEntity);
    }
}
