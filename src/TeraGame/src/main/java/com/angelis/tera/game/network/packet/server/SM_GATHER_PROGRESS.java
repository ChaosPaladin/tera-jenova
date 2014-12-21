package com.angelis.tera.game.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraServerPacket;

public class SM_GATHER_PROGRESS extends TeraServerPacket {

    private final int progress;
    
    public SM_GATHER_PROGRESS(final int progress) {
        this.progress = progress;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeD(byteBuffer, this.progress);
        writeQ(byteBuffer, 0);
    }
}
