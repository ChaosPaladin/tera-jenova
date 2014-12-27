package com.angelis.tera.game.domain.mapper.database;

import java.util.List;

import com.angelis.tera.common.domain.entity.database.AbstractDatabaseEntity;
import com.angelis.tera.common.domain.mapper.database.AbstractDatabaseMapper;
import com.angelis.tera.common.process.model.AbstractModel;
import com.angelis.tera.common.utils.BeanUtils;
import com.angelis.tera.game.domain.entity.database.GatherEntity;
import com.angelis.tera.game.process.model.player.gather.Gather;

public class GatherMapper extends AbstractDatabaseMapper<GatherEntity, Gather> {

    // MODEL -> ENTITY
    @Override
    protected GatherEntity createNewEmptyEntity() {
        return new GatherEntity();
    }

    @Override
    public void map(final Gather model, final GatherEntity entity) {
        entity.setGatherType(model.getGatherType());
        entity.setLevel(model.getLevel());
    }

    @Override
    protected void finalizeDependencies(final Gather model, final GatherEntity entity, final List<Class<? extends AbstractModel>> excludedDependencies) {
        // No dependency
    }

    // ENTITY -> MODEL
    @Override
    protected Gather createNewEmptyModel() {
        return new Gather();
    }

    @Override
    public void map(final GatherEntity entity, final Gather model) {
        model.setGatherType(entity.getGatherType());
        model.setLevel(entity.getLevel());
    }

    @Override
    protected void finalizeDependencies(final GatherEntity entity, final Gather model, final List<Class<? extends AbstractDatabaseEntity>> excludedDependencies) {
        // No dependency
    }

    @Override
    public void merge(final GatherEntity currentEntity, final GatherEntity existingEntity) {
        BeanUtils.fill(GatherEntity.class, currentEntity, existingEntity);
    }
}
