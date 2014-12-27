package com.angelis.tera.game.process.command.user;

import java.util.EnumSet;

import com.angelis.tera.common.process.model.account.enums.AccountTypeEnum;
import com.angelis.tera.game.process.command.AbstractCommand;

public abstract class AbstractUserCommand extends AbstractCommand {
    public abstract EnumSet<? extends AccountTypeEnum> getAllowedAccountTypes();
}
