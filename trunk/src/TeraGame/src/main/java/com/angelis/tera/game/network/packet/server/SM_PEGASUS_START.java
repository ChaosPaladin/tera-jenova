package com.angelis.tera.game.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.player.Player;

public class SM_PEGASUS_START extends TeraServerPacket {

    private final Player player;
    private final int state;
    
    public SM_PEGASUS_START(final Player player, final int state) {
        this.player = player;
        this.state = state;
    }
    
    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeUid(byteBuffer, this.player);
        writeD(byteBuffer, this.state);
    }
}
