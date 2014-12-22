package com.angelis.tera.game.presentation.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.player.Player;

public class SM_PLAYER_BLOCK_NOTE_SUCCESS extends TeraServerPacket {

    public SM_PLAYER_BLOCK_NOTE_SUCCESS(final Player player, final String note) {
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        
    }

}
