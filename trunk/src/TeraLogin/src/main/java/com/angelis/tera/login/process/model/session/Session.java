package com.angelis.tera.login.process.model.session;

import com.angelis.tera.login.process.model.Account;

public class Session {
    
    private Account account;
    
    public Session() {
    }

    public Account getAccount() {
        return this.account;
    }
    
    public void setAccount(final Account account) {
        this.account = account;
    }

    public boolean isLoggedIn() {
        return account != null;
    }

    public boolean isAdmin() {
        return account != null && account.getAccess() > 0;
    }
}
