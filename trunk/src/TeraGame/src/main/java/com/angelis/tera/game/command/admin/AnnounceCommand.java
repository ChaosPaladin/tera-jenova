package com.angelis.tera.game.command.admin;

import org.apache.commons.lang3.StringUtils;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.server.SM_CHAT;
import com.angelis.tera.game.process.model.channel.enums.ChatTypeEnum;
import com.angelis.tera.game.process.services.WorldService;

public class AnnounceCommand extends AbstractAdminCommand {

    @Override
    public void execute(final TeraGameConnection connection, final String[] arguments) {
        final SM_CHAT packet = new SM_CHAT(null, StringUtils.join(arguments, " "), ChatTypeEnum.SYSTEM);
        for (final TeraGameConnection con : WorldService.getInstance().getAllOnlineConnections()) {
            con.sendPacket(packet);
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
        return true;
    }

    @Override
    public String getSyntax() {
        return "announce [message]";
    }
}
