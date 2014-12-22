package com.angelis.tera.game.process.exceptions.command.admin;

public class NoSuchAdminCommandException extends Exception {

    private static final long serialVersionUID = -4953748937612938156L;

    private final String adminCommand;
    
    public NoSuchAdminCommandException(final String adminCommand) {
        this.adminCommand = adminCommand;
    }

    @Override
    public String getMessage() {
        return "No such admin command : "+this.adminCommand;
    }
}
