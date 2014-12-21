package com.angelis.tera.game.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraServerPacket;

public class SM_PLAYER_DEATH_WINDOW extends TeraServerPacket {

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeD(byteBuffer, 30); // time before resurection ?
        writeD(byteBuffer, 0); // resurect item available ?
        writeH(byteBuffer, 0);
        writeH(byteBuffer, 0); // resurect item count ?
        writeH(byteBuffer, 0);
    }

}
