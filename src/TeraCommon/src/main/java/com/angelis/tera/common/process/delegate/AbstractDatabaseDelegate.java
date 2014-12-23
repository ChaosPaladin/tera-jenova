package com.angelis.tera.common.process.delegate;

import java.util.List;

import javolution.util.FastList;

import com.angelis.tera.common.domain.dao.DAOManager;
import com.angelis.tera.common.domain.dao.database.AbstractDatabaseDAO;
import com.angelis.tera.common.domain.entity.database.AbstractDatabaseEntity;
import com.angelis.tera.common.domain.mapper.MapperManager;
import com.angelis.tera.common.domain.mapper.database.AbstractDatabaseMapper;
import com.angelis.tera.common.process.delegate.exception.DelegateException;
import com.angelis.tera.common.process.model.AbstractModel;


public abstract class AbstractDatabaseDelegate<E extends AbstractDatabaseEntity, M extends AbstractModel> {
    
    private final Class<? extends AbstractDatabaseMapper<E, M>> mapperClass;
    private final Class<? extends AbstractDatabaseDAO<E>> daoClass;
    
    public AbstractDatabaseDelegate(final Class<? extends AbstractDatabaseMapper<E, M>> mapperClass, final Class<? extends AbstractDatabaseDAO<E>> daoClass) {
        this.mapperClass = mapperClass;
        this.daoClass = daoClass;
    }
    
    public final M findById(final int id) {
        final E entity = getDAO().findById(id);
        if (entity == null) {
            return null;
        }
        
        return getMapper().map(entity);
    }
    
    public final List<M> findAll() {
        final List<E> entities = getDAO().findAll();
        final List<M> models = new FastList<>();
        if (entities != null && !entities.isEmpty()) {
            for (final E entity : entities) {
                models.add(getMapper().map(entity));
            }
        }
        return models;
    }

    public final void create(final M model) {
        final E entity = getMapper().map(model);
        getDAO().create(entity);
        model.setId(entity.getId());
        
        // Then update the model with the new datas
        getMapper().map(entity, model);
    }

    public final void update(final M model) {
        // Retrieve the entity
        final E existingEntity = getDAO().findById(model.getId());
        if (existingEntity == null) {
            throw new DelegateException("You can't update a not existing entity !");
        }
        
        // Convert the model into entity, merge with existing, and update
        final E currentEntity = getMapper().map(model);
        getMapper().merge(currentEntity, existingEntity);
        getDAO().update(existingEntity);
        
        // Then update the model with the new datas
        getMapper().map(existingEntity, model);
    }

    public final void delete(final M model) {
        final E entity = getMapper().map(model);
        getDAO().delete(entity);
    }

    public AbstractDatabaseMapper<E, M> getMapper() {
        return MapperManager.getDatabaseMapper(mapperClass);
    }
    
    public AbstractDatabaseDAO<E> getDAO() {
        return DAOManager.getDatabaseDAO(daoClass);
    }
}