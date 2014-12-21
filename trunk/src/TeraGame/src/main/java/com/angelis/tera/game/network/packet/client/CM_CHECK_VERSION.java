package com.angelis.tera.game.network.packet.client;

import java.nio.ByteBuffer;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraClientPacket;
import com.angelis.tera.game.network.packet.server.SM_CHECK_VERSION;

public class CM_CHECK_VERSION extends TeraClientPacket {

    public CM_CHECK_VERSION(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        readB(new byte[28]);
    }

    @Override
    protected void runImpl() {
        this.getConnection().sendPacket(new SM_CHECK_VERSION());
    }
}
