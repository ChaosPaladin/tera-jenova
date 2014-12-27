package com.angelis.tera.game.presentation.network.packet.server.player;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.channel.Channel;

public class SM_PLAYER_CHANNEL_INFO extends TeraServerPacket {

    private final Channel channel;
    private final int mapId;
    
    public SM_PLAYER_CHANNEL_INFO(final Channel channel, final int mapId) {
        this.channel = channel;
        this.mapId = mapId;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeD(byteBuffer, this.mapId);
        writeD(byteBuffer, this.channel.getId());
        writeD(byteBuffer, 0); // TODO maybe population
    }
}
