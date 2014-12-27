package com.angelis.tera.game.process.command.admin;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.process.services.WelcomeMessageService;

public class WelcomeMessageCommand extends AbstractAdminCommand  {
    
    private enum CommandEnum {
        ADD,
        SEND,
        ;
    }
    
    @Override
    public void execute(final TeraGameConnection connection, final String[] arguments) {
        final CommandEnum command = CommandEnum.valueOf(arguments[0].toUpperCase());
        switch (command) {
            case ADD:
                final String title = arguments[1];
                final String content = arguments[2];
                
                WelcomeMessageService.getInstance().createNewWelcomeMessage(title, content);
            break;
            
            case SEND:
                WelcomeMessageService.getInstance().sendLastWelcomeMessageToAllPlayers();
            break;
        }
    }

    @Override
    public int getAccessLevel() {
        return 1;
    }

    @Override
    public int getArgumentCount() {
        return 1;
    }

    @Override
    public boolean checkArguments(final String[] arguments) {
        try {
            CommandEnum.valueOf(arguments[0].toUpperCase());
            return true;
        } catch (final Exception e) {
            return false;
        }
    }

    @Override
    public String getSyntax() {
        return "welcomemessage {add | send} [value]";
    }
}
