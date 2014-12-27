package com.angelis.tera.game.presentation.network.packet.server.character;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;

public class SM_CHARACTER_CREATE_NAME_USED_CHECK extends TeraServerPacket {

    private final boolean nameFree;
    
    public SM_CHARACTER_CREATE_NAME_USED_CHECK(final boolean nameFree) {
        this.nameFree = nameFree;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeC(byteBuffer, this.nameFree);
    }
}
