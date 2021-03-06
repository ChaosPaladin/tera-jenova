package com.angelis.tera.game.domain.dao.database;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.angelis.tera.common.domain.dao.database.AbstractDatabaseDAO;
import com.angelis.tera.game.domain.entity.database.PlayerEntity;
import com.angelis.tera.game.process.services.DatabaseService;

public class PlayerDAO extends AbstractDatabaseDAO<PlayerEntity> {

    public PlayerDAO() {
        super(PlayerEntity.class);
    }

    public PlayerEntity findByName(final String name) {
        PlayerEntity player = null;
        
        final Transaction transaction = this.getTransaction();
        
        final Query query = this.createQuery("from PlayerEntity where name = :name");
        query.setString("name", name);
        
        player = (PlayerEntity) query.uniqueResult();
        
        transaction.commit();
        
        return player;
    }

    @Override
    protected Session getSession() {
        return DatabaseService.getInstance().getSessionFactory().openSession();
    }
}
