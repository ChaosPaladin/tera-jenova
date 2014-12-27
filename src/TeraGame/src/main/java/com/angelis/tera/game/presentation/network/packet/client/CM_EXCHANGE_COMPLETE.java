package com.angelis.tera.game.presentation.network.packet.client;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraClientPacket;
import com.angelis.tera.game.process.services.ExchangeService;

public class CM_EXCHANGE_COMPLETE extends TeraClientPacket {

    public CM_EXCHANGE_COMPLETE(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        readQ(); // target uid
        readD(); // requestId
    }

    @Override
    protected void runImpl() {
        ExchangeService.getInstance().completeExchange(this.getConnection().getActivePlayer());
    }

}
