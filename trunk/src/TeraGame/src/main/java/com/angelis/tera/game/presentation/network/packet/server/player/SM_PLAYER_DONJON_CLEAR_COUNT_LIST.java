package com.angelis.tera.game.presentation.network.packet.server.player;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.player.Player;

public class SM_PLAYER_DONJON_CLEAR_COUNT_LIST extends TeraServerPacket {

    public SM_PLAYER_DONJON_CLEAR_COUNT_LIST(final Player findPlayerByName) {
        
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {

    }
}
