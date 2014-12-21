package com.angelis.tera.game.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraServerPacket;

public class SM_ITEM_SIMPLE_INFO extends TeraServerPacket {

    private final int itemId;
    
    public SM_ITEM_SIMPLE_INFO(final int itemId) {
        this.itemId = itemId;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeD(byteBuffer, this.itemId);
        writeQ(byteBuffer, 0);
        writeD(byteBuffer, 0);
        writeH(byteBuffer, 0);
    }
}
