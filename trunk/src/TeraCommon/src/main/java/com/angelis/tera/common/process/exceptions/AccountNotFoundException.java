package com.angelis.tera.common.process.exceptions;

public class AccountNotFoundException extends Exception {

    private static final long serialVersionUID = -3883489166694677707L;
    
    private final String login;

    public AccountNotFoundException(final String login) {
        this.login = login;
    }

    @Override
    public String getMessage() {
        return "Account " + this.login + " tryed to login but this account isn't registered. Closing connection.";
    }
}
