package com.angelis.tera.login.process.delegate.database;

import com.angelis.tera.common.process.delegate.AbstractDatabaseDelegate;
import com.angelis.tera.login.domain.dao.database.ServerDAO;
import com.angelis.tera.login.domain.entity.database.ServerEntity;
import com.angelis.tera.login.domain.mapper.database.ServerMapper;
import com.angelis.tera.login.process.dto.Server;

public class ServerDelegate extends AbstractDatabaseDelegate<ServerEntity, Server> {

    public ServerDelegate() {
        super(ServerMapper.class, ServerDAO.class);
    }
}
