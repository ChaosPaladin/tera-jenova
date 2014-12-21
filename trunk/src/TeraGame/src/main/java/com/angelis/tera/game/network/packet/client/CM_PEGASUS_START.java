package com.angelis.tera.game.network.packet.client;

import java.nio.ByteBuffer;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraClientPacket;
import com.angelis.tera.game.services.PegasusFlyService;

public class CM_PEGASUS_START extends TeraClientPacket {

    private int flyId;
    
    public CM_PEGASUS_START(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        this.flyId = readD();
    }

    @Override
    protected void runImpl() {
        PegasusFlyService.getInstance().onPlayerStartPegasusFly(this.getConnection().getActivePlayer(), this.flyId);
    }
}
