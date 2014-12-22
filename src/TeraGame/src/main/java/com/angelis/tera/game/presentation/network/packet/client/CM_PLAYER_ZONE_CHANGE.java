package com.angelis.tera.game.presentation.network.packet.client;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraClientPacket;
import com.angelis.tera.game.process.services.ZoneService;

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
        ZoneService.getInstance().onPlayerZoneChange(this.getConnection().getActivePlayer(), this.datas);
    }
}
