package com.angelis.tera.game.presentation.network.packet.client;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraClientPacket;
import com.angelis.tera.game.presentation.network.packet.server.SM_RESPONSE_UNIQUE_OBJECT;

public class CM_REQUEST_UNIQUE_OBJECT extends TeraClientPacket {

    public CM_REQUEST_UNIQUE_OBJECT(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        readD(); // UNK
    }

    @Override
    protected void runImpl() {
        this.getConnection().sendPacket(new SM_RESPONSE_UNIQUE_OBJECT());
    }
}
