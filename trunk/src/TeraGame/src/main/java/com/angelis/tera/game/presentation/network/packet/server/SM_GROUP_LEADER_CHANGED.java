package com.angelis.tera.game.presentation.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.services.GroupService;

public class SM_GROUP_LEADER_CHANGED extends TeraServerPacket {

    private final Player player;
    
    public SM_GROUP_LEADER_CHANGED(final Player player) {
        this.player = player;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        final int leaderNameShift = byteBuffer.position();
        writeH(byteBuffer, 0);
        
        writeD(byteBuffer, GroupService.COMMON_GROUP_PACKET_VALUE);
        writeD(byteBuffer, this.player.getId());
        
        this.writeBufferPosition(byteBuffer, leaderNameShift);
        writeS(byteBuffer, this.player.getName());
    }
}
