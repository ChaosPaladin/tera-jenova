package com.angelis.tera.game.presentation.network.packet.client;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraClientPacket;
import com.angelis.tera.game.presentation.network.packet.server.SM_INVENTORY;

public class CM_INVENTORY_SHOW extends TeraClientPacket {

    public CM_INVENTORY_SHOW(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        readD(); // unk 01000000
    }

    @Override
    protected void runImpl() {
        this.getConnection().sendPacket(new SM_INVENTORY(this.getConnection().getActivePlayer(), true));
    }
}
