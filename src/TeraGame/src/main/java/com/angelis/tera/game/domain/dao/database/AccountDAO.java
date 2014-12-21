package com.angelis.tera.game.domain.dao.database;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.angelis.tera.common.domain.dao.database.AbstractDatabaseDAO;
import com.angelis.tera.game.domain.entity.database.AccountEntity;
import com.angelis.tera.game.services.DatabaseService;

public class AccountDAO extends AbstractDatabaseDAO<AccountEntity> {
    
    public AccountDAO() {
        super(AccountEntity.class);
    }

    public AccountEntity findByLogin(final String login) {
        AccountEntity account = null;
        
        final Transaction transaction = session.beginTransaction();
        
        final Query query = session.createQuery("from AccountEntity where login = :login");
        query.setString("login", login);
        
        account = (AccountEntity) query.uniqueResult();
        
        transaction.commit();
        
        return account;
    }

    @Override
    protected Session getSession() {
        return DatabaseService.getInstance().getSessionFactory().openSession();
    }
}
