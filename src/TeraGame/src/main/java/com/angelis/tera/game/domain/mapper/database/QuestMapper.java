package com.angelis.tera.game.domain.mapper.database;

import java.util.List;

import com.angelis.tera.common.domain.entity.database.AbstractDatabaseEntity;
import com.angelis.tera.common.domain.mapper.database.AbstractDatabaseMapper;
import com.angelis.tera.common.process.model.AbstractModel;
import com.angelis.tera.game.domain.entity.database.QuestEntity;
import com.angelis.tera.game.process.model.quest.Quest;

public class QuestMapper extends AbstractDatabaseMapper<QuestEntity, Quest> {

    @Override
    protected QuestEntity createNewEmptyEntity() {
        return new QuestEntity();
    }

    @Override
    public void map(final Quest model, final QuestEntity entity) {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void finalizeDependencies(final Quest model, final QuestEntity entity, final List<Class<? extends AbstractModel>> excludedDependencies) {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected Quest createNewEmptyModel() {
        return new Quest();
    }

    @Override
    public void map(final QuestEntity entity, final Quest model) {
        model.setId(entity.getQuestId());
        // TODO
    }

    @Override
    protected void finalizeDependencies(final QuestEntity entity, final Quest model, final List<Class<? extends AbstractDatabaseEntity>> excludedDependencies) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void merge(final QuestEntity currentEntity, final QuestEntity existingEntity) {
        // TODO Auto-generated method stub
        
    }

}
