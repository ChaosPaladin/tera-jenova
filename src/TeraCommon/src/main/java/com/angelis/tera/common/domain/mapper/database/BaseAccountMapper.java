package com.angelis.tera.common.domain.mapper.database;

import com.angelis.tera.common.domain.entity.database.BaseAccountEntity;
import com.angelis.tera.common.process.model.BaseAccountModel;


public abstract class BaseAccountMapper<E extends BaseAccountEntity, M extends BaseAccountModel> extends AbstractDatabaseMapper<E, M> {
    // MODEL -> ENTITY
    @Override
    public void map(final M model, final E entity) {
        // DIRECT
        entity.setLogin(model.getLogin());
        entity.setPassword(model.getPassword());
        entity.setBanned(model.isBanned());
        entity.setAccess(model.getAccess());
        entity.setLocale(model.getLocale());
        entity.setAuthenticated(model.isAuthenticated());
        entity.setAccountType(model.getAccountType());
        entity.setExtraCharacterSlotCount(model.getExtraCharacterSlotCount());
    }

    // ENTITY -> MODEL
    @Override
    public void map(final E entity, final M model) {
        // DIRECT
        model.setLogin(entity.getLogin());
        model.setPassword(entity.getPassword());
        model.setBanned(entity.isBanned());
        model.setAccess(entity.getAccess());
        model.setLocale(entity.getLocale());
        model.setAuthenticated(entity.isAuthenticated());
        model.setAccountType(entity.getAccountType());
        model.setExtraCharacterSlotCount(entity.getExtraCharacterSlotCount());
    }
}
