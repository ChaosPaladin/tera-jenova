package com.angelis.tera.game.domain.dao.database;

import org.hibernate.Session;

import com.angelis.tera.common.domain.dao.database.AbstractDatabaseDAO;
import com.angelis.tera.game.domain.entity.database.WelcomeMessageEntity;
import com.angelis.tera.game.process.services.DatabaseService;

public class WelcomeMessageDAO extends AbstractDatabaseDAO<WelcomeMessageEntity> {

    public WelcomeMessageDAO() {
        super(WelcomeMessageEntity.class);
    }

    @Override
    protected Session getSession() {
        return DatabaseService.getInstance().getSessionFactory().openSession();
    }

}
