package com.angelis.tera.game.domain.mapper.database;

import java.util.List;

import com.angelis.tera.common.domain.entity.database.AbstractDatabaseEntity;
import com.angelis.tera.common.domain.mapper.database.AbstractDatabaseMapper;
import com.angelis.tera.common.process.model.AbstractModel;
import com.angelis.tera.common.utils.BeanUtils;
import com.angelis.tera.game.domain.entity.database.WelcomeMessageEntity;
import com.angelis.tera.game.process.model.WelcomeMessage;

public class WelcomeMessageMapper extends AbstractDatabaseMapper<WelcomeMessageEntity, WelcomeMessage> {

    // MODEL -> ENTITY
    @Override
    protected WelcomeMessageEntity createNewEmptyEntity() {
        return new WelcomeMessageEntity();
    }

    @Override
    public void map(final WelcomeMessage model, final WelcomeMessageEntity entity) {
        // DIRECT
        entity.setTitle(model.getTitle());
        entity.setContent(model.getContent());
        entity.setCreationDate(model.getCreationDate());
    }

    @Override
    protected void finalizeDependencies(final WelcomeMessage model, final WelcomeMessageEntity entity, final List<Class<? extends AbstractModel>> excludedDependencies) {
        // No dependency
    }

    @Override
    protected WelcomeMessage createNewEmptyModel() {
        return new WelcomeMessage();
    }

    @Override
    public void map(final WelcomeMessageEntity entity, final WelcomeMessage model) {
        // DIRECT
        model.setTitle(entity.getTitle());
        model.setContent(entity.getContent());
        model.setCreationDate(entity.getCreationDate());
    }

    @Override
    protected void finalizeDependencies(final WelcomeMessageEntity entity, final WelcomeMessage model, final List<Class<? extends AbstractDatabaseEntity>> excludedDependencies) {
        // No dependency
    }

    @Override
    public void merge(final WelcomeMessageEntity currentEntity, final WelcomeMessageEntity existingEntity) {
        BeanUtils.fill(WelcomeMessageEntity.class, currentEntity, existingEntity);
    }
}
