package com.angelis.tera.login.domain.entity.database;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.angelis.tera.common.domain.entity.database.BaseAccountEntity;

@Entity
@Table(name = "accounts")
public class AccountEntity extends BaseAccountEntity {

    private static final long serialVersionUID = -3237641768338597721L;

    public AccountEntity(final Integer id) {
        super(id);
    }

    public AccountEntity() {
        super();
    }
}
