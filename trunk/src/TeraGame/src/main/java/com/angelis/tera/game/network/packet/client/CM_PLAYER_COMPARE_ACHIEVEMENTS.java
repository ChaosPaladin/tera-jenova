package com.angelis.tera.game.network.packet.client;

import java.nio.ByteBuffer;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraClientPacket;

public class CM_PLAYER_COMPARE_ACHIEVEMENTS extends TeraClientPacket {

    public CM_PLAYER_COMPARE_ACHIEVEMENTS(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        // TODO Auto-generated method stub
    }

    @Override
    protected void runImpl() {
        // TODO Auto-generated method stub
    }

}
