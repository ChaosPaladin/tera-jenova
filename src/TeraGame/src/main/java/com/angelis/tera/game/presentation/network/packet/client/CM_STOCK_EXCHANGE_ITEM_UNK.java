package com.angelis.tera.game.presentation.network.packet.client;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraClientPacket;
import com.angelis.tera.game.presentation.network.packet.server.SM_STOCK_EXCHANGE_ITEM_UNK;

public class CM_STOCK_EXCHANGE_ITEM_UNK extends TeraClientPacket {

    private int itemId;

    public CM_STOCK_EXCHANGE_ITEM_UNK(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        this.itemId = readD();
        readD(); // 0x00000000
    }

    @Override
    protected void runImpl() {
        this.getConnection().sendPacket(new SM_STOCK_EXCHANGE_ITEM_UNK(this.itemId));
    }
}
