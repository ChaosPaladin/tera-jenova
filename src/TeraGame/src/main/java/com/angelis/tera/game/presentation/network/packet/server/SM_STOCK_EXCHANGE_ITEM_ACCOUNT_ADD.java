package com.angelis.tera.game.presentation.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.player.StockExchangeItem;

public class SM_STOCK_EXCHANGE_ITEM_ACCOUNT_ADD extends TeraServerPacket {

    private final StockExchangeItem stockExchangeItem;
    
    public SM_STOCK_EXCHANGE_ITEM_ACCOUNT_ADD(final StockExchangeItem stockExchangeItem) {
        this.stockExchangeItem = stockExchangeItem;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeD(byteBuffer, this.stockExchangeItem.getUid());
        writeD(byteBuffer, this.stockExchangeItem.getItemId());
        writeD(byteBuffer, this.stockExchangeItem.getAmount());
    }

}
