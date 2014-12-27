package com.angelis.tera.game.presentation.network.packet.server.group;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.services.GroupService;

public class SM_GROUP_MEMBER_MP_UPDATE extends TeraServerPacket {

    private final Player player;
    
    public SM_GROUP_MEMBER_MP_UPDATE(final Player player) {
        this.player = player;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeD(byteBuffer, GroupService.COMMON_GROUP_PACKET_VALUE);
        writeD(byteBuffer, this.player.getId());
        writeD(byteBuffer, this.player.getCurrentStats().getMp());
        writeD(byteBuffer, this.player.getTemplate().getBaseStats().getBaseMp());
    }

}
