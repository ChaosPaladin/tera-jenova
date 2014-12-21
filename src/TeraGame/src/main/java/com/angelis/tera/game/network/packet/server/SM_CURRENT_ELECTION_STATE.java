package com.angelis.tera.game.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraServerPacket;

public class SM_CURRENT_ELECTION_STATE extends TeraServerPacket {

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeD(byteBuffer, 0);
        writeD(byteBuffer, 19);
        writeD(byteBuffer, 0);
        writeH(byteBuffer, 0);
        writeB(byteBuffer, "52E52E52");
        writeD(byteBuffer, 0);
    }
}
