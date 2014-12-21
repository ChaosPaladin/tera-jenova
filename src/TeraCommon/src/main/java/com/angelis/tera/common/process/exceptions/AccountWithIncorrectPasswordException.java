package com.angelis.tera.common.process.exceptions;

public class AccountWithIncorrectPasswordException extends Exception {

    private static final long serialVersionUID = -7110287711037208459L;
    
    private final String login;

    public AccountWithIncorrectPasswordException(final String login) {
        this.login = login;
    }

    @Override
    public String getMessage() {
        return "Account " + this.login + " tryed to login with incorrect password. Closing connection.";
    }
}
