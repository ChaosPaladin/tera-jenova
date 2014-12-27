package com.angelis.tera.common.process.exceptions.account;

public class AccountBannedException extends Exception {

    private static final long serialVersionUID = 701094791199985172L;

    private final String login;

    public AccountBannedException(final String login) {
        this.login = login;
    }
    
    @Override
    public String getMessage() {
        return "Banned account "+this.login+" tryed to login. Closing connection.";
    }

}
