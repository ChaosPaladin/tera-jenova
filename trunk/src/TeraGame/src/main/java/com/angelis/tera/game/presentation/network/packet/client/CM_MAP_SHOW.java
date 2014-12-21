package com.angelis.tera.game.presentation.network.packet.client;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraClientPacket;

public class CM_MAP_SHOW extends TeraClientPacket {

    public CM_MAP_SHOW(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        readD(); // 02 00 00 00
    }

    @Override
    protected void runImpl() {
        
    }
}
