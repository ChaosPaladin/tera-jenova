package com.angelis.tera.game.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraServerPacket;

public class SM_REIGN_INFO extends TeraServerPacket {

    private final int unk;

    public SM_REIGN_INFO(final int unk) {
        this.unk = unk;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        // TODO
        writeB(byteBuffer, "0C000E00");
        writeD(byteBuffer, this.unk);
        writeD(byteBuffer, 0);
    }
}
