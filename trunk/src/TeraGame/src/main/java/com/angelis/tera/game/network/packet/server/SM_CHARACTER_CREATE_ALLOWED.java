package com.angelis.tera.game.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraServerPacket;

/**
 * 
 * @author Angelis
 * Server tell if user can create character
 */
public class SM_CHARACTER_CREATE_ALLOWED extends TeraServerPacket {
    
    private final boolean createAllowed;
    
    public SM_CHARACTER_CREATE_ALLOWED(final boolean createAllowed) {
        this.createAllowed = createAllowed;
    }
    
    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeC(byteBuffer, createAllowed);
    }
}
