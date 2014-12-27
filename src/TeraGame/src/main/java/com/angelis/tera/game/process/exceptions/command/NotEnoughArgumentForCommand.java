package com.angelis.tera.game.process.exceptions.command;

import com.angelis.tera.game.process.command.AbstractCommand;

public class NotEnoughArgumentForCommand extends Exception {

    private static final long serialVersionUID = 6265611859254328303L;

    private final AbstractCommand command;

    public NotEnoughArgumentForCommand(final AbstractCommand command) {
        this.command = command;
    }

    public AbstractCommand getCommand() {
        return this.command;
    }
    
    @Override
    public String getMessage() {
        return "Not enough argument for : " + this.command.getClass().getName();
    }
}
