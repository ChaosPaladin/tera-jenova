package com.angelis.tera.game.presentation.network.packet.server.character;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;

public class SM_CHARACTER_CREATE extends TeraServerPacket {

    private final boolean creationSuccess;

    public SM_CHARACTER_CREATE(final boolean creationSuccess) {
        this.creationSuccess = creationSuccess;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeC(byteBuffer, creationSuccess);
    }
}
