package com.angelis.tera.game.presentation.network.packet.server;

import java.nio.ByteBuffer;
import java.util.List;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.channel.Channel;

public class SM_PLAYER_CHANNEL_LIST extends TeraServerPacket {

    private final int mapId;
    private final List<Channel> channels;
    
    public SM_PLAYER_CHANNEL_LIST(final int mapId, final List<Channel> channels) {
        this.mapId = mapId;
        this.channels = channels;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) { 
        writeH(byteBuffer, this.channels.size());
        writeB(byteBuffer, "1000");
        writeH(byteBuffer, 0); // first channel shift
        writeB(byteBuffer, "0000");
        writeD(byteBuffer, this.mapId);
        
        this.writeBufferPosition(byteBuffer, 8);
        for (final Channel channel : this.channels) {
            final int shiftPosition = byteBuffer.position();
            writeH(byteBuffer, shiftPosition);
            writeH(byteBuffer, 0); // next channel shift
            writeD(byteBuffer, channel.getId());
            writeD(byteBuffer, channel.getPlayers().size());
            this.writeBufferPosition(byteBuffer, shiftPosition+2);
        }
    }

}
