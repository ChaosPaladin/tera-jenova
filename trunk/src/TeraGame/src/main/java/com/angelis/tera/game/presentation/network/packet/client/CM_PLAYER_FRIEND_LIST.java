package com.angelis.tera.game.presentation.network.packet.client;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraClientPacket;
import com.angelis.tera.game.presentation.network.packet.server.SM_PLAYER_FRIEND_LIST;

public class CM_PLAYER_FRIEND_LIST extends TeraClientPacket {

    public CM_PLAYER_FRIEND_LIST(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        // Empty packet
    }

    @Override
    protected void runImpl() {
        this.getConnection().sendPacket(new SM_PLAYER_FRIEND_LIST(this.getConnection().getActivePlayer()));
    }
}
