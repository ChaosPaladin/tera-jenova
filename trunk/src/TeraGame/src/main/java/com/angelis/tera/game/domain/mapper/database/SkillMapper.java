package com.angelis.tera.game.domain.mapper.database;

import java.util.List;

import com.angelis.tera.common.domain.entity.database.AbstractDatabaseEntity;
import com.angelis.tera.common.domain.mapper.database.AbstractDatabaseMapper;
import com.angelis.tera.common.process.model.AbstractModel;
import com.angelis.tera.common.utils.BeanUtils;
import com.angelis.tera.game.domain.entity.database.SkillEntity;
import com.angelis.tera.game.process.model.skill.Skill;

public class SkillMapper extends AbstractDatabaseMapper<SkillEntity, Skill> {

    // MODEL -> ENTITY
    @Override
    protected SkillEntity createNewEmptyEntity() {
        return new SkillEntity();
    }

    @Override
    public void map(final Skill model, final SkillEntity entity) {
        entity.setLevel(model.getLevel());
        entity.setSkillId(model.getSkillId());
    }

    @Override
    protected void finalizeDependencies(final Skill model, final SkillEntity entity, final List<Class<? extends AbstractModel>> excludedDependencies) {
        // No dependency
    }

    // ENTITY -> MODEL
    @Override
    protected Skill createNewEmptyModel() {
        return new Skill();
    }

    @Override
    public void map(final SkillEntity entity, final Skill model) {
        model.setLevel(entity.getLevel());
        model.setSkillId(entity.getSkillId());
    }

    @Override
    protected void finalizeDependencies(final SkillEntity entity, final Skill model, final List<Class<? extends AbstractDatabaseEntity>> excludedDependencies) {
        // No dependency
    }

    @Override
    public void merge(final SkillEntity currentEntity, final SkillEntity existingEntity) {
        BeanUtils.fill(SkillEntity.class, currentEntity, existingEntity);
    }
}
