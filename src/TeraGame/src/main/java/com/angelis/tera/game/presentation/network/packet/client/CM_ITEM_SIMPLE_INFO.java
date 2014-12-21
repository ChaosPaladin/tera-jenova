package com.angelis.tera.game.presentation.network.packet.client;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraClientPacket;
import com.angelis.tera.game.presentation.network.packet.server.SM_ITEM_SIMPLE_INFO;

public class CM_ITEM_SIMPLE_INFO extends TeraClientPacket {

    private int itemId;

    public CM_ITEM_SIMPLE_INFO(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        this.itemId = readD();
    }

    @Override
    protected void runImpl() {
        this.getConnection().sendPacket(new SM_ITEM_SIMPLE_INFO(this.itemId));
    }

}
