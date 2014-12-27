package com.angelis.tera.game.presentation.network.packet.client;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraClientPacket;
import com.angelis.tera.game.process.services.ExchangeService;

public class CM_EXCHANGE_ITEM_ADD_BUY extends TeraClientPacket {

    private int itemId;
    private int amount;

    public CM_EXCHANGE_ITEM_ADD_BUY(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        readQ(); // target uid
        readD(); // requestId

        this.itemId = readD();
        this.amount = readD();
    }

    @Override
    protected void runImpl() {
        ExchangeService.getInstance().addBuyItem(this.getConnection().getActivePlayer(), this.itemId, this.amount);
    }

}
