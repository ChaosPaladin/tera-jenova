package com.angelis.tera.game.presentation.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.player.Player;

public class SM_PLAYER_FRIEND_ADD_SUCCESS extends TeraServerPacket {

    private final Player player;
    
    
    public SM_PLAYER_FRIEND_ADD_SUCCESS(final Player player) {
        this.player = player;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeB(byteBuffer, "2800");
        writeD(byteBuffer, player.getId());
        writeD(byteBuffer, this.player.getLevel());
        writeD(byteBuffer, this.player.getRace().value);
        writeD(byteBuffer, this.player.getPlayerClass().value);
        writeB(byteBuffer, this.player.getCurrentZoneData());
        writeB(byteBuffer, "100000000000"); // unk
        writeS(byteBuffer, player.getName());
    }
}
