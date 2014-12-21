package com.angelis.tera.game.network.packet.client;

import java.nio.ByteBuffer;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraClientPacket;
import com.angelis.tera.game.services.ChannelService;

public class CM_CHANNEL_LIST extends TeraClientPacket {

    private int channelId;
    private int mapId;
    
    public CM_CHANNEL_LIST(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        this.channelId = readD();
        this.mapId = readD();
    }

    @Override
    protected void runImpl() {
        ChannelService.getInstance().onPlayerRequestChannelList(this.getConnection().getActivePlayer(), this.channelId, this.mapId);
    }
}
