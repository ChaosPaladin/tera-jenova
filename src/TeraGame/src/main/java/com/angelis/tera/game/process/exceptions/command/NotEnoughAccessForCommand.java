package com.angelis.tera.game.process.exceptions.command;

public class NotEnoughAccessForCommand extends Exception {

    private static final long serialVersionUID = 1510677624277138221L;

    private final String commandName;

    public NotEnoughAccessForCommand(final String commandName) {
        this.commandName = commandName;
    }

    @Override
    public String getMessage() {
        return "Not enough access for : "+this.commandName;
    }
}
