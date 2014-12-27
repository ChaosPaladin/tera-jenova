package com.angelis.tera.game.presentation.network.packet.server.character;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;

public class SM_CHARACTER_DELETE extends TeraServerPacket {

    private final boolean deleteSuccess;

    public SM_CHARACTER_DELETE(final boolean deleteSuccess) {
        this.deleteSuccess = deleteSuccess;
    }
    
    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeC(byteBuffer, deleteSuccess);
    }
}
