package com.angelis.tera.game.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraServerPacket;

public class SM_SIMPLE_TIP_REPEATED_CHECK extends TeraServerPacket {

    private final int data;
    
    public SM_SIMPLE_TIP_REPEATED_CHECK(final int data) {
        this.data = data;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeD(byteBuffer, this.data);
        writeC(byteBuffer, 1);
    }
}
