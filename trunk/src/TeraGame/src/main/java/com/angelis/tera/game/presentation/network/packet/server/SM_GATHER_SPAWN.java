package com.angelis.tera.game.presentation.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.gather.Gather;
import com.angelis.tera.game.process.model.visible.WorldPosition;

public class SM_GATHER_SPAWN extends TeraServerPacket {

    private final Gather gather;
    
    public SM_GATHER_SPAWN(final Gather gather) {
        this.gather = gather;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        final WorldPosition worldPosition = this.gather.getWorldPosition();
        
        writeUid(byteBuffer, this.gather);
        writeD(byteBuffer, this.gather.getId());
        writeD(byteBuffer, this.gather.getRemainingGather()); //gather counter
        writeF(byteBuffer, worldPosition.getX());
        writeF(byteBuffer, worldPosition.getY());
        writeF(byteBuffer, worldPosition.getZ());
        
        writeD(byteBuffer, 0);
        writeD(byteBuffer, 0);
    }
}
