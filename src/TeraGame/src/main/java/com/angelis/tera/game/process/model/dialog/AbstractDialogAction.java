package com.angelis.tera.game.process.model.dialog;

import com.angelis.tera.game.process.model.player.Player;

public abstract class AbstractDialogAction {
    
    protected final Player player;
    protected final Dialog dialog;
    
    public AbstractDialogAction(final Player player, final Dialog dialog) {
        this.player = player;
        this.dialog = dialog;
    }
    
    public abstract void action();
}
