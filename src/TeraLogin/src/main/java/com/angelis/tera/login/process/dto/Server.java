package com.angelis.tera.login.process.dto;

import com.angelis.tera.common.process.model.AbstractModel;
import com.angelis.tera.login.process.model.enums.LanguageEnum;

public class Server extends AbstractModel {

    private String ip;

    private int port;

    private String serverCategory;

    private String serverName;

    private String serverCrowdness;

    private String serverOpen;

    private String permissionMask;

    private String serverStat;

    private String popup;

    private LanguageEnum language;

    public String getIp() {
        return ip;
    }
    
    public Server(final Integer id) {
        super(id);
    }

    public Server() {
        super(null);
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

    public void setIp(final String ip) {
        this.ip = ip;
    }

}
