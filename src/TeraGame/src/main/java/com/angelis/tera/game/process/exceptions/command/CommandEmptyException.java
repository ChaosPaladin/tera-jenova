package com.angelis.tera.game.process.exceptions.command;

public class CommandEmptyException extends Exception {

    private static final long serialVersionUID = -6952401116953124302L;

    @Override
    public String getMessage() {
        return "User tryed an empty command";
    }
}
