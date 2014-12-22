package com.angelis.tera.game.domain.dao.database;

import org.hibernate.Session;

import com.angelis.tera.common.domain.dao.database.AbstractDatabaseDAO;
import com.angelis.tera.game.domain.entity.database.GuildEntity;
import com.angelis.tera.game.process.services.DatabaseService;

public class GuildDAO extends AbstractDatabaseDAO<GuildEntity> {

    public GuildDAO() {
        super(GuildEntity.class);
    }

    @Override
    protected Session getSession() {
        return DatabaseService.getInstance().getSessionFactory().openSession();
    }
}
