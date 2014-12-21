package com.angelis.tera.game.network.packet.client;

import java.nio.ByteBuffer;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraClientPacket;
import com.angelis.tera.game.network.packet.server.SM_REIGN_INFO;

public class CM_REIGN_INFO extends TeraClientPacket {

    private int unk;
    
    public CM_REIGN_INFO(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        this.unk = readD();
    }

    @Override
    protected void runImpl() {
        this.getConnection().sendPacket(new SM_REIGN_INFO(this.unk));
    }

}
