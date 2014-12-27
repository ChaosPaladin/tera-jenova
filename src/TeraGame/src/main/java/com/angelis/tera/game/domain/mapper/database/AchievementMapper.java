package com.angelis.tera.game.domain.mapper.database;

import java.util.List;

import com.angelis.tera.common.domain.entity.database.AbstractDatabaseEntity;
import com.angelis.tera.common.domain.mapper.database.AbstractDatabaseMapper;
import com.angelis.tera.common.process.model.AbstractModel;
import com.angelis.tera.common.utils.BeanUtils;
import com.angelis.tera.game.domain.entity.database.AchievementEntity;
import com.angelis.tera.game.process.model.player.Achievement;

public class AchievementMapper extends AbstractDatabaseMapper<AchievementEntity, Achievement> {

    @Override
    protected AchievementEntity createNewEmptyEntity() {
        return new AchievementEntity();
    }

    @Override
    public void map(final Achievement model, final AchievementEntity entity) {
        // TODO
    }

    @Override
    protected void finalizeDependencies(final Achievement model, final AchievementEntity entity, final List<Class<? extends AbstractModel>> excludedDependencies) {
        // No dependency
    }

    @Override
    protected Achievement createNewEmptyModel() {
        return new Achievement();
    }

    @Override
    public void map(final AchievementEntity entity, final Achievement model) {
        // TODO
    }

    @Override
    protected void finalizeDependencies(final AchievementEntity entity, final Achievement model, final List<Class<? extends AbstractDatabaseEntity>> excludedDependencies) {
        // No dependency
    }

    @Override
    public void merge(final AchievementEntity currentEntity, final AchievementEntity existingEntity) {
        BeanUtils.fill(AchievementEntity.class, currentEntity, existingEntity);
    }
}
