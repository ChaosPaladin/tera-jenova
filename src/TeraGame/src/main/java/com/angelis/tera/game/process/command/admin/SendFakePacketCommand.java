package com.angelis.tera.game.process.command.admin;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.server.SM_OPCODE_LESS_PACKET;

public class SendFakePacketCommand extends AbstractAdminCommand {

    @Override
    public void execute(final TeraGameConnection connection, final String[] arguments) {
        connection.sendPacket(new SM_OPCODE_LESS_PACKET("F4FA0600400031003500310034000000"));
    }

    @Override
    public int getAccessLevel() {
        return 0;
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
        return "sendfakepacket [data]";
    }
}
