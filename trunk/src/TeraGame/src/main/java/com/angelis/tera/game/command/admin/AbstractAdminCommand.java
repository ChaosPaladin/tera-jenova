package com.angelis.tera.game.command.admin;

import com.angelis.tera.game.command.AbstractCommand;

public abstract class AbstractAdminCommand extends AbstractCommand {
    public abstract int getAccessLevel();
}
