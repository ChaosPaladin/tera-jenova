package com.angelis.tera.game.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.player.Player;

public class SM_RESPONSE_ACCOUNT_OBJECT extends TeraServerPacket {

    public SM_RESPONSE_ACCOUNT_OBJECT(final Player player) {
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        // 030008000800140013000000A30100001400200014000000A501000020000000170000009C010000
    }

}
