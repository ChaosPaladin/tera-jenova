package com.angelis.tera.game.presentation.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;

public class SM_STOCK_EXCHANGE_ITEM_UNK extends TeraServerPacket {

    private final int itemId;

    public SM_STOCK_EXCHANGE_ITEM_UNK(final int itemId) {
        this.itemId = itemId;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeD(byteBuffer, this.itemId);
        writeB(byteBuffer, "000000000000000000000000000000000000");
    }

}
