package com.angelis.tera.login.domain.entity.database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.angelis.tera.common.domain.entity.database.AbstractDatabaseEntity;
import com.angelis.tera.login.process.model.enums.LanguageEnum;

@Entity
@Table(name="servers")
public class ServerEntity extends AbstractDatabaseEntity {

    private static final long serialVersionUID = -1506148037250430137L;

    @Column(name = "ip")
    private String ip;

    @Column(name = "port")
    private int port;

    @Column(name = "category")
    private String serverCategory;

    @Column(name = "name")
    private String serverName;

    @Column(name = "crowdness")
    private String serverCrowdness;

    @Column(name = "open")
    private String serverOpen;

    @Column(name = "permission_mask")
    private String permissionMask;

    @Column(name = "server_stat")
    private String serverStat;

    @Column(name = "popup")
    private String popup;

    @Column(name = "language")
    private LanguageEnum language;

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

    public String getServerCategory() {
        return serverCategory;
    }

    public void setServerCategory(final String serverCategory) {
        this.serverCategory = serverCategory;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(final String serverName) {
        this.serverName = serverName;
    }

    public String getServerCrowdness() {
        return serverCrowdness;
    }

    public void setServerCrowdness(final String serverCrowdness) {
        this.serverCrowdness = serverCrowdness;
    }

    public String getServerOpen() {
        return serverOpen;
    }

    public void setServerOpen(final String serverOpen) {
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

    public LanguageEnum getLanguage() {
        return language;
    }

    public void setLanguage(final LanguageEnum language) {
        this.language = language;
    }
}
