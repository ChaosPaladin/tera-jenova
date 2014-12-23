package com.angelis.tera.login.domain.entity.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.angelis.tera.common.domain.entity.xml.AbstractXMLEntity;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "server")
public class ServerEntity extends AbstractXMLEntity {

    private static final long serialVersionUID = 8506717527338223279L;

    @XmlElement(name = "id")
    private Integer id;

    @XmlElement(name = "ip")
    private String ip;

    @XmlElement(name = "port")
    private int port;

    @XmlElement(name = "category")
    private ServerCategoryEntity serverCategory;

    @XmlElement(name = "name")
    private ServerNameEntity serverName;

    @XmlElement(name = "crowdness")
    private ServerCrowdnessEntity serverCrowdness;

    @XmlElement(name = "open")
    private ServerOpenEntity serverOpen;

    @XmlElement(name = "permission_mask")
    private String permissionMask;

    @XmlElement(name = "server_stat")
    private String serverStat;

    @XmlElement(name = "popup")
    private String popup;

    @XmlElement(name = "language")
    private String language;

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(final String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(final int port) {
        this.port = port;
    }

    public ServerCategoryEntity getServerCategory() {
        return serverCategory;
    }

    public void setServerCategory(final ServerCategoryEntity serverCategory) {
        this.serverCategory = serverCategory;
    }

    public ServerNameEntity getServerName() {
        return serverName;
    }

    public void setServerName(final ServerNameEntity serverName) {
        this.serverName = serverName;
    }

    public ServerCrowdnessEntity getServerCrowdness() {
        return serverCrowdness;
    }

    public void setServerCrowdness(final ServerCrowdnessEntity serverCrowdness) {
        this.serverCrowdness = serverCrowdness;
    }

    public ServerOpenEntity getServerOpen() {
        return serverOpen;
    }

    public void setServerOpen(final ServerOpenEntity serverOpen) {
        this.serverOpen = serverOpen;
    }

    public String getPermissionMask() {
        return permissionMask;
    }

    public void setPermissionMask(final String permissionMask) {
        this.permissionMask = permissionMask;
    }

    public String getServerStat() {
        return serverStat;
    }

    public void setServerStat(final String serverStat) {
        this.serverStat = serverStat;
    }

    public String getPopup() {
        return popup;
    }

    public void setPopup(final String popup) {
        this.popup = popup;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(final String language) {
        this.language = language;
    }

    @Override
    public void onLoad() {
        // TODO Auto-generated method stub

    }
}
