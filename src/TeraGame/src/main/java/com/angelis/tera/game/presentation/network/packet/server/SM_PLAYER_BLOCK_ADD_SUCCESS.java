package com.angelis.tera.game.presentation.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.player.Player;

public class SM_PLAYER_BLOCK_ADD_SUCCESS extends TeraServerPacket {

    private final Player player;
    
    public SM_PLAYER_BLOCK_ADD_SUCCESS(final Player player) {
        this.player = player;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeB(byteBuffer, "0C001600"); // unk
        writeD(byteBuffer, this.player.getId());
        writeS(byteBuffer, this.player.getName());
        writeH(byteBuffer, 0); // unk
    }
}
