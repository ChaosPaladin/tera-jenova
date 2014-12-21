package com.angelis.tera.game.network.packet.client;

import java.nio.ByteBuffer;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraClientPacket;

public class CM_GLYPH_REINIT extends TeraClientPacket {

    public CM_GLYPH_REINIT(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        readD();
        readC();
    }

    @Override
    protected void runImpl() {
        // TODO
    }
}
