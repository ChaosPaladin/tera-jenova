package com.angelis.tera.game.presentation.network.packet.client.key;

import java.nio.ByteBuffer;

import com.angelis.tera.common.network.crypt.CryptSession;
import com.angelis.tera.common.network.packet.AbstractClientPacket;
import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.server.key.KeyServerPacket;

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
        session.readKeyPacket(this.data);
        
        connection.sendPacket(new KeyServerPacket());
    }
}   
