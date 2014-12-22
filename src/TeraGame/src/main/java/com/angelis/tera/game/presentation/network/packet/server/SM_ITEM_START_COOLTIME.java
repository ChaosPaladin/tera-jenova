package com.angelis.tera.game.presentation.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;

public class SM_ITEM_START_COOLTIME extends TeraServerPacket {

    private final int itemId;
    private final int cooltime;

    public SM_ITEM_START_COOLTIME(final int itemId, final int cooltime) {
        this.itemId = itemId;
        this.cooltime = cooltime;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeD(byteBuffer, this.itemId);
        writeD(byteBuffer, this.cooltime);
    }
}
