package com.angelis.tera.game.presentation.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.campfire.CampFire;

public class SM_CAMPFIRE_DESPAWN extends TeraServerPacket {

    private final CampFire campFire;

    public SM_CAMPFIRE_DESPAWN(final CampFire campFire) {
        this.campFire = campFire;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeUid(byteBuffer, this.campFire);
        writeC(byteBuffer, this.campFire.getCampFireType().value);
    }
}
