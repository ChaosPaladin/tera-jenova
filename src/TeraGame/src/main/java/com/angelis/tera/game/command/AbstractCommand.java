package com.angelis.tera.game.command;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.server.SM_CHAT;
import com.angelis.tera.game.process.model.channel.enums.ChatTypeEnum;
import com.angelis.tera.game.process.services.I18nService;

public abstract class AbstractCommand {
    
    public final void sendErrorMessage(final TeraGameConnection connection, final String message) {
        connection.sendPacket(new SM_CHAT(null, message, ChatTypeEnum.SYSTEM));
    }
    
    public final void sendTranslatedErrorMessage(final TeraGameConnection connection, final String key, final Object... args) {
        final String message = I18nService.getInstance().translate(connection.getAccount().getLocale(), key, args);
        this.sendErrorMessage(connection, message);
    }
    
    public abstract void execute(TeraGameConnection connection, String[] arguments);
    public abstract int getArgumentCount();
    public abstract boolean checkArguments(String[] arguments);
    public abstract String getSyntax();
}
