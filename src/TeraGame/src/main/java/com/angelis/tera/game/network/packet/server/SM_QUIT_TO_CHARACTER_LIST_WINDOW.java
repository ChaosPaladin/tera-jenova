package com.angelis.tera.game.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraServerPacket;

/**
 * @author Angelis
 * 
 * This packet is sent to client to show wait windows before send him to character list.
 */
public class SM_QUIT_TO_CHARACTER_LIST_WINDOW extends TeraServerPacket {

    private final int timeOut;
    
    public SM_QUIT_TO_CHARACTER_LIST_WINDOW(final int timeOut) {
        this.timeOut = timeOut;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeD(byteBuffer, timeOut);
    }
}
