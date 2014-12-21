package com.angelis.tera.game.presentation.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;

public class SM_OPTION_SHOW_MASK extends TeraServerPacket {

    private final boolean maskVisible;
    
    public SM_OPTION_SHOW_MASK(final boolean maskVisible) {
        this.maskVisible = maskVisible;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeUid(byteBuffer, connection.getActivePlayer());
        writeC(byteBuffer, this.maskVisible);
    }
}
