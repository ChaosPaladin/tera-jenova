package com.angelis.tera.login.domain.mapper.database;

import java.util.List;

import com.angelis.tera.common.domain.entity.database.AbstractDatabaseEntity;
import com.angelis.tera.common.domain.mapper.database.AbstractDatabaseMapper;
import com.angelis.tera.common.process.model.AbstractModel;
import com.angelis.tera.login.domain.entity.database.ServerEntity;
import com.angelis.tera.login.process.dto.Server;

public class ServerMapper extends AbstractDatabaseMapper<ServerEntity, Server> {

    // MODEL -> ENTITY
    @Override
    protected ServerEntity createNewEmptyEntity() {
        return new ServerEntity();
    }

    @Override
    public void map(final Server model, final ServerEntity entity) {
        // DIRECT
        entity.setIp(model.getIp());
        entity.setLanguage(model.getLanguage());
        entity.setPermissionMask(model.getPermissionMask());
        entity.setPopup(model.getPopup());
        entity.setPort(model.getPort());
        entity.setServerCategory(model.getServerCategory());
        entity.setServerCrowdness(model.getServerCrowdness());
        entity.setServerName(model.getServerName());
        entity.setServerOpen(model.getServerOpen());
        entity.setServerStat(model.getServerStat());
    }

    @Override
    protected void finalizeDependencies(final Server model, final ServerEntity entity, final List<Class<? extends AbstractModel>> excludedDependencies) {
        // No dependency
    }

    // ENTITY -> MODEL
    @Override
    protected Server createNewEmptyModel() {
        return new Server();
    }

    @Override
    public void map(final ServerEntity entity, final Server model) {
        // DIRECT
        model.setIp(entity.getIp());
        model.setLanguage(entity.getLanguage());
        model.setPermissionMask(entity.getPermissionMask());
        model.setPopup(entity.getPopup());
        model.setPort(entity.getPort());
        model.setServerCategory(entity.getServerCategory());
        model.setServerCrowdness(entity.getServerCrowdness());
        model.setServerName(entity.getServerName());
        model.setServerOpen(entity.getServerOpen());
        model.setServerStat(entity.getServerStat());
    }

    @Override
    protected void finalizeDependencies(final ServerEntity entity, final Server model, final List<Class<? extends AbstractDatabaseEntity>> excludedDependencies) {
        // No dependency
    }

    @Override
    public void merge(final ServerEntity currentEntity, final ServerEntity existingEntity) {
        // TODO Auto-generated method stub
    }
}
