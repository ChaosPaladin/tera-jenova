package com.angelis.tera.game.presentation.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;

public class SM_PLAYER_ZONE_CHANGE extends TeraServerPacket {

    private final byte[] zoneData;
    
    public SM_PLAYER_ZONE_CHANGE(final byte[] zoneData) {
        this.zoneData = zoneData;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeC(byteBuffer, 0);
        writeB(byteBuffer, this.zoneData);
    }
}
