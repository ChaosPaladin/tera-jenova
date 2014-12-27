package com.angelis.tera.game.presentation.network.packet.client;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraClientPacket;
import com.angelis.tera.game.process.services.StockExchangeService;

public class CM_STOCK_EXCHANGE_ITEM_ACCOUNT_LIST extends TeraClientPacket {

    public CM_STOCK_EXCHANGE_ITEM_ACCOUNT_LIST(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        // Empty packet
    }

    @Override
    protected void runImpl() {
        StockExchangeService.getInstance().onStockExchangeItemAccountList(this.getConnection().getActivePlayer());
    }
}
