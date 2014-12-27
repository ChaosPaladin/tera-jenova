package com.angelis.tera.game.process.delegate.database;

import com.angelis.tera.common.process.delegate.AbstractDatabaseDelegate;
import com.angelis.tera.game.domain.dao.database.AccountDAO;
import com.angelis.tera.game.domain.entity.database.AccountEntity;
import com.angelis.tera.game.domain.mapper.database.AccountMapper;
import com.angelis.tera.game.process.model.account.Account;

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
