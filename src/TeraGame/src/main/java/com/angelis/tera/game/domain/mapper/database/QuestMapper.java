package com.angelis.tera.game.domain.mapper.database;

import java.util.List;

import com.angelis.tera.common.domain.entity.database.AbstractDatabaseEntity;
import com.angelis.tera.common.domain.mapper.database.AbstractDatabaseMapper;
import com.angelis.tera.common.process.model.AbstractModel;
import com.angelis.tera.game.domain.entity.database.QuestEnvEntity;
import com.angelis.tera.game.process.model.quest.Quest;

public class QuestMapper extends AbstractDatabaseMapper<QuestEnvEntity, Quest> {

    @Override
    protected QuestEnvEntity createNewEmptyEntity() {
        return new QuestEnvEntity();
    }

    @Override
    public void map(final Quest model, final QuestEnvEntity entity) {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void finalizeDependencies(final Quest model, final QuestEnvEntity entity, final List<Class<? extends AbstractModel>> excludedDependencies) {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected Quest createNewEmptyModel() {
        return new Quest();
    }

    @Override
    public void map(final QuestEnvEntity entity, final Quest model) {
        model.setId(entity.getQuestId());
        // TODO
    }

    @Override
    protected void finalizeDependencies(final QuestEnvEntity entity, final Quest model, final List<Class<? extends AbstractDatabaseEntity>> excludedDependencies) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void merge(final QuestEnvEntity currentEntity, final QuestEnvEntity existingEntity) {
        // TODO Auto-generated method stub
        
    }

}
