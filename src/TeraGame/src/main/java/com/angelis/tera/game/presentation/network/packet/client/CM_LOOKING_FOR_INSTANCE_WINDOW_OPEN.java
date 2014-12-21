package com.angelis.tera.game.presentation.network.packet.client;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraClientPacket;

public class CM_LOOKING_FOR_INSTANCE_WINDOW_OPEN extends TeraClientPacket {

    public CM_LOOKING_FOR_INSTANCE_WINDOW_OPEN(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void readImpl() {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void runImpl() {
        // TODO Auto-generated method stub
        
    }

}
