package com.angelis.tera.game.process.command.admin;

import com.angelis.tera.game.process.command.AbstractCommand;

public abstract class AbstractAdminCommand extends AbstractCommand {
    public abstract int getAccessLevel();
}
