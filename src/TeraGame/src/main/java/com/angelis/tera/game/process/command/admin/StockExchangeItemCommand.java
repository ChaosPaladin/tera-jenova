package com.angelis.tera.game.process.command.admin;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.process.command.AdminErrorMessageEnum;
import com.angelis.tera.game.process.model.account.Account;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.services.StockExchangeService;
import com.angelis.tera.game.process.services.WorldService;

public class StockExchangeItemCommand extends AbstractAdminCommand {
    
    enum CommandEnum {
        ADD
        ;
    }
    
    enum TypeEnum {
        UNIQUE,
        ACCOUNT,
        ;
    }
    
    @Override
    public void execute(final TeraGameConnection connection, final String[] arguments) {
        final Player targetPlayer = WorldService.getInstance().getOnlinePlayerByName(arguments[1]);
        if (targetPlayer == null) {
            this.sendTranslatedErrorMessage(connection, AdminErrorMessageEnum.TARGET_NOT_FOUND.key);
            return;
        }

        final Account targetAccount = targetPlayer.getAccount();
        if (targetAccount.getAccess() > connection.getAccount().getAccess()) {
            this.sendTranslatedErrorMessage(connection, AdminErrorMessageEnum.TARGET_HAS_MORE_RIGHTS_THAN_YOU.key);
            return;
        }
        
        final CommandEnum command = CommandEnum.valueOf(arguments[0].toUpperCase());
        switch (command) {
            case ADD:
                final int itemId = Integer.parseInt(arguments[2]);
                final int itemAmount = Integer.parseInt(arguments[3]);
                
                final TypeEnum type = TypeEnum.valueOf(arguments[4].toUpperCase());
                switch (type) {
                    case ACCOUNT:
                        StockExchangeService.getInstance().addPlayerAccountStockEchangeItem(targetAccount, itemId, itemAmount);
                    break;
                    
                    case UNIQUE:
                        StockExchangeService.getInstance().addPlayerUniqueStockEchangeItem(targetPlayer, itemId, itemAmount);
                    break;
                }
                
            break;
        }
    }

    @Override
    public int getAccessLevel() {
        return 1;
    }

    @Override
    public int getArgumentCount() {
        return 5;
    }

    @Override
    public boolean checkArguments(final String[] arguments) {
        try {
            CommandEnum.valueOf(arguments[0].toUpperCase());
            Integer.parseInt(arguments[2]);
            Integer.parseInt(arguments[3]);
            TypeEnum.valueOf(arguments[4].toUpperCase());
            return true;
        } catch (final Exception e) {
            return false;
        }
    }

    @Override
    public String getSyntax() {
        return "stockexchangeitem add [playerName] [itemId] [amount] {UNIQUE | ACCOUNT}";
    }
}
