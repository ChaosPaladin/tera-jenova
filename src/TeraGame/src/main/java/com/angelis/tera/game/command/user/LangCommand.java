package com.angelis.tera.game.command.user;

import java.util.EnumSet;

import org.apache.commons.lang3.LocaleUtils;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.process.model.account.enums.AccountTypeEnum;

public class LangCommand extends AbstractUserCommand {

    @Override
    public void execute(final TeraGameConnection connection, final String[] arguments) {
        connection.getAccount().setLocale(LocaleUtils.toLocale(arguments[0]));
    }

    @Override
    public EnumSet<? extends AccountTypeEnum> getAllowedAccountTypes() {
        return EnumSet.allOf(AccountTypeEnum.class);
    }
    
    @Override
    public int getArgumentCount() {
        return 0;
    }

    @Override
    public boolean checkArguments(final String[] arguments) {
        try {
            LocaleUtils.toLocale(arguments[0]);
            return true;
        } catch (final Exception e) {
            return false;
        }
    }

    @Override
    public String getSyntax() {
        return "lang {en|fr}";
    }
}
