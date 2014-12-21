package com.angelis.tera.common.domain.entity.database;

import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseAccountEntity extends AbstractDatabaseEntity {

    private static final long serialVersionUID = -4034792019245322102L;

    @Column(unique = true, name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "banned")
    private boolean banned;

    @Column(name = "access")
    private int access;

    @Column(name = "locale")
    private Locale locale;

    @Column(name = "authenticated")
    private boolean authenticated;

    public BaseAccountEntity(final Integer id) {
        super(id);
    }

    public BaseAccountEntity() {
        super();
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(final String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public boolean isBanned() {
        return banned;
    }

    public void setBanned(final boolean banned) {
        this.banned = banned;
    }

    public int getAccess() {
        return access;
    }

    public void setAccess(final int access) {
        this.access = access;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(final Locale locale) {
        this.locale = locale;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(final boolean authenticated) {
        this.authenticated = authenticated;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + access;
        result = prime * result + (authenticated ? 1231 : 1237);
        result = prime * result + (banned ? 1231 : 1237);
        result = prime * result + ((locale == null) ? 0 : locale.hashCode());
        result = prime * result + ((login == null) ? 0 : login.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof BaseAccountEntity)) {
            return false;
        }
        final BaseAccountEntity other = (BaseAccountEntity) obj;
        if (access != other.access) {
            return false;
        }
        if (authenticated != other.authenticated) {
            return false;
        }
        if (banned != other.banned) {
            return false;
        }
        if (locale == null) {
            if (other.locale != null) {
                return false;
            }
        }
        else if (!locale.equals(other.locale)) {
            return false;
        }
        if (login == null) {
            if (other.login != null) {
                return false;
            }
        }
        else if (!login.equals(other.login)) {
            return false;
        }
        if (password == null) {
            if (other.password != null) {
                return false;
            }
        }
        else if (!password.equals(other.password)) {
            return false;
        }
        return true;
    }

}
