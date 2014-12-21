package com.angelis.tera.common.domain.mapper.database;

import java.util.List;

import javolution.util.FastList;

import com.angelis.tera.common.domain.entity.database.AbstractDatabaseEntity;
import com.angelis.tera.common.process.model.AbstractModel;

public abstract class AbstractDatabaseMapper<E extends AbstractDatabaseEntity, M extends AbstractModel> {

    private final List<Class<? extends AbstractDatabaseEntity>> emptyEntityList = new FastList<>(0);
    private final List<Class<? extends AbstractModel>> emptyModelList = new FastList<>(0);

    // ENTITY RELATED
    public final E map(final M model) {
        return this.map(model, emptyModelList);
    }

    public final E map(final M model, final List<Class<? extends AbstractModel>> excludedDependencies) {
        final E entity = createNewEmptyEntity();
        entity.setId(model.getId());
        this.map(model, entity);
        this.finalizeDependencies(model, entity, excludedDependencies);

        return entity;
    }
    
    protected abstract E createNewEmptyEntity();
    public abstract void map(final M model, final E entity);
    protected abstract void finalizeDependencies(final M model, final E entity, List<Class<? extends AbstractModel>> excludedDependencies);
    
    // MODEL RELATED
    public final M map(final E entity) {
        return this.map(entity, emptyEntityList);
    }
    
    public final M map(final E entity, final List<Class<? extends AbstractDatabaseEntity>> excludedDependencies) {
        final M model = createNewEmptyModel();
        model.setId(entity.getId());
        this.map(entity, model);
        this.finalizeDependencies(entity, model, excludedDependencies);
        
        return model;
    }

    protected abstract M createNewEmptyModel();
    public abstract void map(final E entity, final M model);
    protected abstract void finalizeDependencies(final E entity, final M model, List<Class<? extends AbstractDatabaseEntity>> excludedDependencies);
    public abstract void merge(final E currentEntity, final E existingEntity);
}
