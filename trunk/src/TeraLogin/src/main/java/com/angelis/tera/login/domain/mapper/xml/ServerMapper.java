package com.angelis.tera.login.domain.mapper.xml;

import com.angelis.tera.common.domain.mapper.xml.AbstractXMLMapper;
import com.angelis.tera.login.domain.entity.xml.ServerCategoryEntity;
import com.angelis.tera.login.domain.entity.xml.ServerCrowdnessEntity;
import com.angelis.tera.login.domain.entity.xml.ServerEntity;
import com.angelis.tera.login.domain.entity.xml.ServerNameEntity;
import com.angelis.tera.login.domain.entity.xml.ServerOpenEntity;
import com.angelis.tera.login.process.dto.Server;


public class ServerMapper extends AbstractXMLMapper<ServerEntity, Server> {

    @Override
    protected ServerEntity createNewEmptyEntity() {
        return new ServerEntity();
    }

    @Override
    public void map(final Server model, final ServerEntity entity) {
        // DIRECT
        entity.setId(model.getId());
        entity.setIp(model.getIp());
        entity.setLanguage(model.getLanguage().name().toLowerCase());
        entity.setPermissionMask(model.getPermissionMask());
        entity.setPopup("<![CDATA["+model.getPopup()+"]]>");
        entity.setPort(model.getPort());
        entity.setServerStat(model.getServerStat());
    }
    
    @Override
    protected void finalizeDependencies(final Server model, final ServerEntity entity) {
        // CATEGORY
        final ServerCategoryEntity serverCategory = new ServerCategoryEntity();
        serverCategory.setValue(model.getServerCategory());
        serverCategory.setSort(2); // TODO
        entity.setServerCategory(serverCategory);
        
        // CROWDNESS
        final ServerCrowdnessEntity serverCrowdness = new ServerCrowdnessEntity();
        serverCrowdness.setValue(model.getServerCrowdness());
        serverCrowdness.setSort(1); // TODO
        entity.setServerCrowdness(serverCrowdness);
        
        // NAME
        final ServerNameEntity serverName = new ServerNameEntity();
        serverName.setValue("<![CDATA["+model.getServerName()+"]]>"); // TODO add character counter here
        serverName.setRawName(model.getServerName());
        entity.setServerName(serverName);
        
        // OPEN
        final ServerOpenEntity serverOpen = new ServerOpenEntity();
        serverOpen.setValue(model.getServerOpen());
        serverOpen.setSort(1);// TODO
        entity.setServerOpen(serverOpen);
    }

    @Override
    protected Server createNewEmptyModel() {
        return new Server();
    }

    @Override
    public void map(final ServerEntity entity, final Server model) {
        
    }

    @Override
    protected void finalizeDependencies(final ServerEntity entity, final Server model) {
        
    }
}
