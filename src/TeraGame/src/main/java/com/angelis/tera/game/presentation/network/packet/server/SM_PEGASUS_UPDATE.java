package com.angelis.tera.game.presentation.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.player.Player;

public class SM_PEGASUS_UPDATE extends TeraServerPacket {

    private final Player player;
    private final int flyId;
    private final int state;
    private final int time;
    
    public SM_PEGASUS_UPDATE(final Player player, final int flyId, final int state, final int time) {
        this.player = player;
        this.flyId = flyId;
        this.state = state;
        this.time = time;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeUid(byteBuffer, this.player);
        writeD(byteBuffer, this.flyId);
        writeD(byteBuffer, this.state);
        writeD(byteBuffer, this.time);
    }

}
