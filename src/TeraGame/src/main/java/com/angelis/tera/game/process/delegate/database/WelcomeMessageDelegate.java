package com.angelis.tera.game.process.delegate.database;

import com.angelis.tera.common.process.delegate.AbstractDatabaseDelegate;
import com.angelis.tera.game.domain.dao.database.WelcomeMessageDAO;
import com.angelis.tera.game.domain.entity.database.WelcomeMessageEntity;
import com.angelis.tera.game.domain.mapper.database.WelcomeMessageMapper;
import com.angelis.tera.game.process.model.WelcomeMessage;

public class WelcomeMessageDelegate extends AbstractDatabaseDelegate<WelcomeMessageEntity, WelcomeMessage>  {

    public WelcomeMessageDelegate() {
        super(WelcomeMessageMapper.class, WelcomeMessageDAO.class);
    }

}
