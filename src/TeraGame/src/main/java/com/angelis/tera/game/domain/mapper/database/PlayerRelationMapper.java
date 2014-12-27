package com.angelis.tera.game.domain.mapper.database;

import java.util.List;

import com.angelis.tera.common.domain.entity.database.AbstractDatabaseEntity;
import com.angelis.tera.common.domain.mapper.MapperManager;
import com.angelis.tera.common.domain.mapper.database.AbstractDatabaseMapper;
import com.angelis.tera.common.process.model.AbstractModel;
import com.angelis.tera.common.utils.CollectionUtils;
import com.angelis.tera.game.domain.entity.database.PlayerRelationEntity;
import com.angelis.tera.game.process.model.player.PlayerRelation;

public class PlayerRelationMapper extends AbstractDatabaseMapper<PlayerRelationEntity, PlayerRelation> {

    @Override
    protected PlayerRelationEntity createNewEmptyEntity() {
        return new PlayerRelationEntity();
    }

    @Override
    public void map(final PlayerRelation model, final PlayerRelationEntity entity) {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void finalizeDependencies(final PlayerRelation model, final PlayerRelationEntity entity, final List<Class<? extends AbstractModel>> excludedDependencies) {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected PlayerRelation createNewEmptyModel() {
        return new PlayerRelation();
    }

    @Override
    public void map(final PlayerRelationEntity entity, final PlayerRelation model) {
        model.setNote(entity.getNote());
        model.setFriend(model.isFriend());
    }

    @Override
    protected void finalizeDependencies(final PlayerRelationEntity entity, final PlayerRelation model, final List<Class<? extends AbstractDatabaseEntity>> excludedDependencies) {
        final List<Class<? extends AbstractDatabaseEntity>> dependenciesToExclude = CollectionUtils.createListFrom(PlayerRelationEntity.class);
        
        if (!excludedDependencies.contains(PlayerRelationEntity.class)) {
            final PlayerMapper playerMapper = MapperManager.getDatabaseMapper(PlayerMapper.class);
            model.setTarget(playerMapper.map(entity.getTarget(), dependenciesToExclude));
        }
    }

    @Override
    public void merge(final PlayerRelationEntity currentEntity, final PlayerRelationEntity existingEntity) {
        // TODO Auto-generated method stub
        
    }

}
