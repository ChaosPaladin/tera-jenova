package com.angelis.tera.game.presentation.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.visible.WorldPosition;

public class SM_LOAD_TOPO extends TeraServerPacket {

    private final WorldPosition worldPosition;
    
    public SM_LOAD_TOPO(final WorldPosition worldPosition) {
        this.worldPosition = worldPosition;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeD(byteBuffer, this.worldPosition.getMapId());
        writeF(byteBuffer, this.worldPosition.getX());
        writeF(byteBuffer, this.worldPosition.getY());
        writeF(byteBuffer, this.worldPosition.getZ());
        writeC(byteBuffer, 0); //NOT Heading
    }
}
