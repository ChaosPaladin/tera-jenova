package com.angelis.tera.game.presentation.network.packet.server.gather;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.player.Player;

public class SM_GATHERCRAFT_POINT extends TeraServerPacket {

    private final Player player;
    
    public SM_GATHERCRAFT_POINT(final Player player) {
        this.player = player;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeD(byteBuffer, 1);
        writeD(byteBuffer, this.player.getGatherCraftPoints());
    }
}
