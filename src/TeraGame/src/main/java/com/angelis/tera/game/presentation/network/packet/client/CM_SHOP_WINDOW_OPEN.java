package com.angelis.tera.game.presentation.network.packet.client;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraClientPacket;
import com.angelis.tera.game.presentation.network.packet.server.SM_SHOP_WINDOW_OPEN;

public class CM_SHOP_WINDOW_OPEN extends TeraClientPacket {

    public CM_SHOP_WINDOW_OPEN(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        // Empty packet
    }

    @Override
    protected void runImpl() {
        this.getConnection().sendPacket(new SM_SHOP_WINDOW_OPEN());
    }

}
