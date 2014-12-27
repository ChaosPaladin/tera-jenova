package com.angelis.tera.game.process.command.user;

import java.util.EnumSet;

import com.angelis.tera.game.process.command.AbstractCommand;
import com.angelis.tera.game.process.model.account.enums.AccountTypeEnum;

public abstract class AbstractUserCommand extends AbstractCommand {
    public abstract EnumSet<? extends AccountTypeEnum> getAllowedAccountTypes();
}
