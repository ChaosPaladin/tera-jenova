package com.angelis.tera.game.presentation.network.packet.client;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraClientPacket;
import com.angelis.tera.game.presentation.network.packet.server.SM_TRADEBROKER_HIGHEST_ITEM_LEVEL;

public class CM_TRADEBROKER_HIGHEST_ITEM_LEVEL extends TeraClientPacket {

    public CM_TRADEBROKER_HIGHEST_ITEM_LEVEL(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        // Empty packet
    }

    @Override
    protected void runImpl() {
        this.getConnection().sendPacket(new SM_TRADEBROKER_HIGHEST_ITEM_LEVEL());
    }
}
