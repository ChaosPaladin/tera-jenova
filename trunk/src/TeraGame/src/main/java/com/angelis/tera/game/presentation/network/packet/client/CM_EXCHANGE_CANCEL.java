package com.angelis.tera.game.presentation.network.packet.client;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraClientPacket;
import com.angelis.tera.game.process.services.ExchangeService;

public class CM_EXCHANGE_CANCEL extends TeraClientPacket {

    public CM_EXCHANGE_CANCEL(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        readD(); // unk
        readD(); // requestId
    }

    @Override
    protected void runImpl() {
        ExchangeService.getInstance().cancelExchange(this.getConnection().getActivePlayer());
    }

}
