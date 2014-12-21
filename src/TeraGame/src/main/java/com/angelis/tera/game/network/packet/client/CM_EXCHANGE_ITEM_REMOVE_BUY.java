package com.angelis.tera.game.network.packet.client;

import java.nio.ByteBuffer;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraClientPacket;
import com.angelis.tera.game.services.ExchangeService;

public class CM_EXCHANGE_ITEM_REMOVE_BUY extends TeraClientPacket {

    private int itemId;
    private int amount;

    public CM_EXCHANGE_ITEM_REMOVE_BUY(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
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
        ExchangeService.getInstance().removeBuyItem(this.getConnection().getActivePlayer(), this.itemId, this.amount);
    }

}
