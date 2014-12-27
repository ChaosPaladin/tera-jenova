package com.angelis.tera.game.presentation.network.packet.server.player;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.player.Player;

public class SM_PLAYER_UNK extends TeraServerPacket {

    private final Player player;

    public SM_PLAYER_UNK(final Player player) {
        this.player = player;
    }
    
    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        // TODO
        writeUid(byteBuffer, this.player);
        writeQ(byteBuffer, 0);
        writeD(byteBuffer, 0);
        writeC(byteBuffer, 1);
    }

}
