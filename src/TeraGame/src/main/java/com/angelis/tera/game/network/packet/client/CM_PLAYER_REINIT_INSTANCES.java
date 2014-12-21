package com.angelis.tera.game.network.packet.client;

import java.nio.ByteBuffer;

import com.angelis.tera.game.network.SystemMessages;
import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraClientPacket;

public class CM_PLAYER_REINIT_INSTANCES extends TeraClientPacket {

    public CM_PLAYER_REINIT_INSTANCES(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        // Empty packet
    }

    @Override
    protected void runImpl() {
        // TODO
        this.getConnection().sendPacket(SystemMessages.NO_INSTANCE_TO_REINIT());
    }

}
