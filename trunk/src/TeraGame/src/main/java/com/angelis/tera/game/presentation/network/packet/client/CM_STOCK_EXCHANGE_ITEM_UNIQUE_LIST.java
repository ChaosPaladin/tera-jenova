package com.angelis.tera.game.presentation.network.packet.client;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraClientPacket;
import com.angelis.tera.game.process.services.StockExchangeService;

public class CM_STOCK_EXCHANGE_ITEM_UNIQUE_LIST extends TeraClientPacket {

    public CM_STOCK_EXCHANGE_ITEM_UNIQUE_LIST(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        readD(); // 0x00000000
    }

    @Override
    protected void runImpl() {
        StockExchangeService.getInstance().onStockExchangeItemUniqueList(this.getConnection().getActivePlayer());
    }
}
