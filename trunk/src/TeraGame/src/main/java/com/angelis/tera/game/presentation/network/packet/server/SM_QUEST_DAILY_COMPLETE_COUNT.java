package com.angelis.tera.game.presentation.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.player.Player;

public class SM_QUEST_DAILY_COMPLETE_COUNT extends TeraServerPacket {

    private final Player player;
    
    public SM_QUEST_DAILY_COMPLETE_COUNT(final Player player) {
        this.player = player;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        // TODO
        writeH(byteBuffer, 0); // player quest daily complete count
        writeH(byteBuffer, 10);// maybe max count
        writeC(byteBuffer, 0);
    }
}
