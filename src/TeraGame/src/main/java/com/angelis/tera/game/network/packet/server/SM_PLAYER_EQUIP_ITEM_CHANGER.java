package com.angelis.tera.game.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraServerPacket;

public class SM_PLAYER_EQUIP_ITEM_CHANGER extends TeraServerPacket {

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        // TODO
        writeB(byteBuffer, "0A0010004F430E000080000210001C0001000000000000001C002800030000000000000028003400040000000000000034004000050000000000000040004C0006000000000000004C005800070000000000000058006400080000000000000064007000090000000000000070007C000A000000000000007C0000000B00000000000000");
    }
}
