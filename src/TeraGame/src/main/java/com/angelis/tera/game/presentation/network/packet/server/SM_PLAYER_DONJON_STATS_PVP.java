package com.angelis.tera.game.presentation.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.player.Player;

public class SM_PLAYER_DONJON_STATS_PVP extends TeraServerPacket {

    public SM_PLAYER_DONJON_STATS_PVP(final Player player) {

    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        // TODO
        writeB(byteBuffer, "D855040000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000E803000000000000000000000000000000000000000000000000000000000000000000000000000000000000E80300000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000E803000000000000");
    }
}
