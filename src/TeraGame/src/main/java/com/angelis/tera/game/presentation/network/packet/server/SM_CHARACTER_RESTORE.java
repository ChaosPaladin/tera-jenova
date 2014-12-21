package com.angelis.tera.game.presentation.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;

public class SM_CHARACTER_RESTORE extends TeraServerPacket {

    private final boolean restoreSuccess;
    
    public SM_CHARACTER_RESTORE(final boolean restoreSuccess) {
        this.restoreSuccess = restoreSuccess;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeC(byteBuffer, restoreSuccess);
    }

}
