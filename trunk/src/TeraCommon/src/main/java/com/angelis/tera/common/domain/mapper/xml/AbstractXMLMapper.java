package com.angelis.tera.common.domain.mapper.xml;

import com.angelis.tera.common.domain.entity.xml.AbstractXMLEntity;
import com.angelis.tera.common.process.model.AbstractModel;

public abstract class AbstractXMLMapper<E extends AbstractXMLEntity, M extends AbstractModel> {

    // ENTITY RELATED
    public final E map(final M model) {
        final E entity = createNewEmptyEntity();
        this.map(model, entity);
        this.finalizeDependencies(model, entity);

        return entity;
    }

    protected E createNewEmptyEntity() {
        return null;
    }

    public void map(final M model, final E entity) {

    }

    protected void finalizeDependencies(final M model, final E entity) {

    }

    // MODEL RELATED
    public final M map(final E entity) {
        final M model = createNewEmptyModel();
        this.map(entity, model);
        this.finalizeDependencies(entity, model);

        return model;
    }

    protected abstract M createNewEmptyModel();

    public abstract void map(final E entity, final M model);

    protected abstract void finalizeDependencies(final E entity, final M model);
}
