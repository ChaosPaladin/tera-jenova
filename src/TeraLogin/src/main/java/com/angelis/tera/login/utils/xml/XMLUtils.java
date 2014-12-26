package com.angelis.tera.login.utils.xml;

import java.util.List;

import com.angelis.tera.common.domain.mapper.MapperManager;
import com.angelis.tera.login.domain.entity.xml.ServerListEntity;
import com.angelis.tera.login.domain.mapper.xml.ServerMapper;
import com.angelis.tera.login.process.dto.Server;

public class XMLUtils {

    public static ServerListEntity convertServers(final List<Server> servers) {
        final ServerListEntity serverListEntity = new ServerListEntity();
        for (final Server server : servers) {
            serverListEntity.getServers().add(MapperManager.getXMLMapper(ServerMapper.class).map(server));
        }
        return serverListEntity;
    }

}
