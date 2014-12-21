package com.angelis.tera.login.process.delegate.database;

import com.angelis.tera.common.process.delegate.AbstractDatabaseDelegate;
import com.angelis.tera.login.domain.dao.database.AccountDAO;
import com.angelis.tera.login.domain.entity.database.AccountEntity;
import com.angelis.tera.login.domain.mapper.database.AccountMapper;
import com.angelis.tera.login.process.model.Account;

public class AccountDelegate extends AbstractDatabaseDelegate<AccountEntity, Account> {

    public AccountDelegate() {
        super(AccountMapper.class, AccountDAO.class);
    }

    public Account findByLogin(final String login) {
        final AccountEntity entity = getDAO().findByLogin(login);
        if (entity == null) {
            return null;
        }
        
        return getMapper().map(entity);
    }

    @Override
    public AccountDAO getDAO() {
        return (AccountDAO) super.getDAO();
    }
}
