package com.angelis.tera.game.presentation.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;

public class SM_QUIT_GAME_WINDOW extends TeraServerPacket {

    private final int timeOut;

    public SM_QUIT_GAME_WINDOW(final int timeOut) {
        this.timeOut = timeOut;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeD(byteBuffer, this.timeOut);
    }
}
