package com.angelis.tera.game.network.packet.client;

import java.nio.ByteBuffer;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraClientPacket;

public class CM_GROUP_CONFIRM_LEADER_CHANGE extends TeraClientPacket {

    private int playerId;

    public CM_GROUP_CONFIRM_LEADER_CHANGE(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        readD(); // Group common packet
        this.playerId = readD();
    }

    @Override
    protected void runImpl() {
        // TODO Auto-generated method stub
        
    }
}
