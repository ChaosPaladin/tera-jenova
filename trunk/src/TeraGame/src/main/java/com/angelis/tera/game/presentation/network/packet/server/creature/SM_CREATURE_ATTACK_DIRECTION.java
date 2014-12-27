package com.angelis.tera.game.presentation.network.packet.server.creature;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;

public class SM_CREATURE_ATTACK_DIRECTION extends TeraServerPacket {

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        /*
         * S->C Packet opCode[0xC581|50561 0xC5000081]:[64]
        Data:
        [0]     400081C5 01002000 01003000 05090E00 @..... ...0.....
        [16]    00800002 A6280000 CC290004 D1A08806 .....(...)......
        [32]    20000000 00000000 B4F10600 00800C00  ...............
        [48]    30000000 9B9AC747 E18C8345 3D1B6044 0......G...E=.`D
         */
    }

}
