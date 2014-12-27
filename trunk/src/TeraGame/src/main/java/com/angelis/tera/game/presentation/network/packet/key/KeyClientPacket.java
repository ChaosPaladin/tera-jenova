package com.angelis.tera.game.presentation.network.packet.key;

import java.nio.ByteBuffer;

import com.angelis.tera.common.presentation.network.crypt.CryptSession;
import com.angelis.tera.common.presentation.network.packet.AbstractClientPacket;
import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;

public class KeyClientPacket extends AbstractClientPacket<TeraGameConnection> {

    private byte[] data = null;

    public KeyClientPacket(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    
    @Override
    protected void readImpl() {
        this.data = readB(128);
    }

    @Override
    protected void runImpl() {
        final TeraGameConnection connection = this.getConnection();
        final CryptSession session = connection.getCryptSession();
        session.readClientKeyPacket(this.data);
        
        connection.sendPacket(new KeyServerPacket());
    }
}   
