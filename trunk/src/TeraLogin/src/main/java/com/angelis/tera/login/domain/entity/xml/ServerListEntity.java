package com.angelis.tera.login.domain.entity.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javolution.util.FastList;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "serverlist")
public class ServerListEntity {
    
    @XmlElement(name = "server")
    private List<ServerEntity> servers;

    public List<ServerEntity> getServers() {
        if (servers == null) {
            servers = new FastList<>(0);
        }
        return servers;
    }

    public void setServers(final List<ServerEntity> servers) {
        this.servers = servers;
    }
}
