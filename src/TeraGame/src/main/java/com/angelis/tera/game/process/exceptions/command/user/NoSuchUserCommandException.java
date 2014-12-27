package com.angelis.tera.game.process.exceptions.command.user;

public class NoSuchUserCommandException extends Exception {

    private static final long serialVersionUID = -4953748937612938156L;

    private final String userCommand;
    
    public NoSuchUserCommandException(final String userCommand) {
        this.userCommand = userCommand;
    }

    @Override
    public String getMessage() {
        return "No such user command : "+this.userCommand;
    }
}
