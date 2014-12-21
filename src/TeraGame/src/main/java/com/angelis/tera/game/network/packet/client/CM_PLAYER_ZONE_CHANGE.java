package com.angelis.tera.game.network.packet.client;

import java.nio.ByteBuffer;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraClientPacket;
import com.angelis.tera.game.services.WorldService;

public class CM_PLAYER_ZONE_CHANGE extends TeraClientPacket {

    private byte[] datas;
    
    public CM_PLAYER_ZONE_CHANGE(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        this.datas = readB(12);
    }

    @Override
    protected void runImpl() {
        WorldService.getInstance().onPlayerZoneChange(this.getConnection().getActivePlayer(), this.datas);
    }
}
