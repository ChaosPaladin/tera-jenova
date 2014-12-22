package com.angelis.tera.game.presentation.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.enums.DespawnTypeEnum;
import com.angelis.tera.game.process.model.gather.Gather;

public class SM_GATHER_DESPAWN extends TeraServerPacket {

    private final Gather gather;
    private final DespawnTypeEnum despawnType;

    public SM_GATHER_DESPAWN(final Gather gather, final DespawnTypeEnum despawnType) {
        this.gather = gather;
        this.despawnType = despawnType;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeUid(byteBuffer, this.gather);
        writeC(byteBuffer, this.despawnType.value);
    }
}
