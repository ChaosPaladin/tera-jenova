package com.angelis.tera.game.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraServerPacket;

public class SM_PET_INCUBATOR_INFO_CHANGE extends TeraServerPacket {

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        // TODO
        writeB(byteBuffer, "0500160001000000000000003C000000000016002200000000000000000022002E00FFFFFFFF000000002E003A00FFFFFFFF000000003A004600FFFFFFFF0000000046000000FFFFFFFF00000000");
    }
}
