package com.angelis.tera.game.presentation.network.packet.key;

import java.nio.ByteBuffer;

import com.angelis.tera.common.presentation.network.crypt.CryptSession;
import com.angelis.tera.common.presentation.network.packet.AbstractServerPacket;
import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;

public class KeyServerPacket extends AbstractServerPacket<TeraGameConnection> {

    @Override
    public void write(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        final CryptSession cryptSession = connection.getCryptSession();
        writeB(byteBuffer, cryptSession.sendKeyPacket());
        
        byteBuffer.flip();
        byteBuffer.position(0);
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer buffer) {
        // never called
    }
    
    @Override
    public void setOpcode(final Short opcode) {
        // useless
    }

    @Override
    public boolean showInDebug() {
        return false;
    }
}
