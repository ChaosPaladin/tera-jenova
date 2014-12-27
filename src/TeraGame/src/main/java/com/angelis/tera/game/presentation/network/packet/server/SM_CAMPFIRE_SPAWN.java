package com.angelis.tera.game.presentation.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.campfire.CampFire;

public class SM_CAMPFIRE_SPAWN extends TeraServerPacket {

    private final CampFire campFire;

    public SM_CAMPFIRE_SPAWN(final CampFire campFire) {
        this.campFire = campFire;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeD(byteBuffer, 0);
        writeUid(byteBuffer, this.campFire);
        writeD(byteBuffer, this.campFire.getCampFireType().value);
        writeF(byteBuffer, this.campFire.getWorldPosition().getX());
        writeF(byteBuffer, this.campFire.getWorldPosition().getY());
        writeF(byteBuffer, this.campFire.getWorldPosition().getZ());
        writeD(byteBuffer, this.campFire.getCampFireStatus().value);
    }
}
