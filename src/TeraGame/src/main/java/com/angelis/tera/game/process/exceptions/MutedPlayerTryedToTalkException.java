package com.angelis.tera.game.process.exceptions;

public class MutedPlayerTryedToTalkException extends Exception {

    private static final long serialVersionUID = 8530843474553653841L;
    
    private final String playerName;

    public MutedPlayerTryedToTalkException(final String playerName) {
        this.playerName = playerName;
    
    }
    @Override
    public String getMessage() {
        return "Muted player " + this.playerName + " tryed to talk";
    }
}
