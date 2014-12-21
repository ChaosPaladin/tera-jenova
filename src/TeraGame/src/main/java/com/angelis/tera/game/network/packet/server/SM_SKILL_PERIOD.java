package com.angelis.tera.game.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.player.Player;

public class SM_SKILL_PERIOD extends TeraServerPacket {

    private final Player player;
    
    public SM_SKILL_PERIOD(final Player player) {
        this.player = player;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeH(byteBuffer, 1); // probably size
        writeH(byteBuffer, 10); // probably first skill period shit
        
        writeUid(byteBuffer, this.player);
        writeH(byteBuffer, 10); // probably this skill period shit
        writeH(byteBuffer, 0);
        
        writeB(byteBuffer, "43B20100BF528B530000000000000000"); // time left
    }

}
