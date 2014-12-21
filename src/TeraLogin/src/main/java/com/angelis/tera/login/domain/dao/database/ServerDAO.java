package com.angelis.tera.login.domain.dao.database;

import org.hibernate.Session;

import com.angelis.tera.common.domain.dao.database.AbstractDatabaseDAO;
import com.angelis.tera.login.domain.entity.database.ServerEntity;
import com.angelis.tera.login.services.DatabaseService;

public class ServerDAO extends AbstractDatabaseDAO<ServerEntity> {

    public ServerDAO() {
        super(ServerEntity.class);
    }

    @Override
    protected Session getSession() {
        return DatabaseService.getInstance().getSessionFactory().openSession();
    }
}
