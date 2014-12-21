package com.angelis.tera.game.network.packet.client;

import java.nio.ByteBuffer;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraClientPacket;
import com.angelis.tera.game.network.packet.server.SM_SIMPLE_TIP_REPEATED_CHECK;

public class CM_SIMPLE_TIP_REPEATED_CHECK extends TeraClientPacket {

    private int data;
    
    public CM_SIMPLE_TIP_REPEATED_CHECK(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        this.data = readD();
    }

    @Override
    protected void runImpl() {
        this.getConnection().sendPacket(new SM_SIMPLE_TIP_REPEATED_CHECK(data));
    }
}
