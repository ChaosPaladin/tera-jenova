package com.angelis.tera.login.domain.mapper.database;

import java.util.List;

import com.angelis.tera.common.domain.entity.database.AbstractDatabaseEntity;
import com.angelis.tera.common.domain.mapper.database.BaseAccountMapper;
import com.angelis.tera.common.process.model.AbstractModel;
import com.angelis.tera.common.utils.BeanUtils;
import com.angelis.tera.login.domain.entity.database.AccountEntity;
import com.angelis.tera.login.process.model.Account;

public class AccountMapper extends BaseAccountMapper<AccountEntity, Account> {
    
    // MODEL -> ENTITY
    @Override
    protected AccountEntity createNewEmptyEntity() {
        return new AccountEntity();
    }

    @Override
    protected void finalizeDependencies(final Account model, final AccountEntity entity, final List<Class<? extends AbstractModel>> excludedDependencies) {
        // No dependency
    }

    // ENTITY -> MODEL
    @Override
    protected Account createNewEmptyModel() {
        return new Account();
    }

    @Override
    protected void finalizeDependencies(final AccountEntity entity, final Account model, final List<Class<? extends AbstractDatabaseEntity>> excludedDependencies) {
        // No dependency
    }

    @Override
    public void merge(final AccountEntity currentEntity, final AccountEntity existingEntity) {
        BeanUtils.fill(AccountEntity.class, currentEntity, existingEntity);
    }
}
