package com.angelis.tera.game.domain.mapper.database;

import java.util.List;

import com.angelis.tera.common.domain.entity.database.AbstractDatabaseEntity;
import com.angelis.tera.common.domain.mapper.database.AbstractDatabaseMapper;
import com.angelis.tera.common.process.model.AbstractModel;
import com.angelis.tera.game.domain.entity.database.GuildEntity;
import com.angelis.tera.game.process.model.guild.Guild;

public class GuildMapper extends AbstractDatabaseMapper<GuildEntity, Guild> {

    @Override
    protected GuildEntity createNewEmptyEntity() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void map(final Guild model, final GuildEntity entity) {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void finalizeDependencies(final Guild model, final GuildEntity entity, final List<Class<? extends AbstractModel>> excludedDependencies) {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected Guild createNewEmptyModel() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void map(final GuildEntity entity, final Guild model) {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void finalizeDependencies(final GuildEntity entity, final Guild model, final List<Class<? extends AbstractDatabaseEntity>> excludedDependencies) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void merge(final GuildEntity currentEntity, final GuildEntity existingEntity) {
        // TODO Auto-generated method stub
        
    }

    
}
