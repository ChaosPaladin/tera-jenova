package com.angelis.tera.game.presentation.network.packet.server.group;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.services.GroupService;

public class SM_GROUP_MEMBER_MOVE extends TeraServerPacket {

    private final Player player;
    
    public SM_GROUP_MEMBER_MOVE(final Player player) {
        this.player = player;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeD(byteBuffer, GroupService.COMMON_GROUP_PACKET_VALUE);
        writeD(byteBuffer, this.player.getId());
        writeF(byteBuffer, this.player.getWorldPosition().getX());
        writeF(byteBuffer, this.player.getWorldPosition().getY());
        writeF(byteBuffer, this.player.getWorldPosition().getZ());
        writeD(byteBuffer, this.player.getWorldPosition().getMapId());
        writeD(byteBuffer, 1);
    }

}
