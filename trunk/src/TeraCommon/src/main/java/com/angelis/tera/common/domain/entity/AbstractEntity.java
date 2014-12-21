package com.angelis.tera.common.domain.entity;

import com.angelis.tera.common.domain.entity.database.HasId;

public abstract class AbstractEntity implements HasId {

    @Override
    public Integer getId() {
        return 0;
    }

    public void setId(final Integer id) {
        
    }
}
