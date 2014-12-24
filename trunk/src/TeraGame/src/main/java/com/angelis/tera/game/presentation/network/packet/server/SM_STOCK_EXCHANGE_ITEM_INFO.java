package com.angelis.tera.game.presentation.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.player.StockExchangeItem;

public class SM_STOCK_EXCHANGE_ITEM_INFO extends TeraServerPacket {

    private StockExchangeItem stockExchangeItem = new StockExchangeItem();

    public SM_STOCK_EXCHANGE_ITEM_INFO(final StockExchangeItem stockExchangeItem) {
        this.stockExchangeItem = stockExchangeItem;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeH(byteBuffer, 1); // Item count (always 1)

        final int endDescriptionShift = byteBuffer.position();
        writeH(byteBuffer, 0);

        writeB(byteBuffer, "1200"); // unk

        writeUid(byteBuffer, stockExchangeItem);
        writeS(byteBuffer, stockExchangeItem.getDescription());

        writeBufferPosition(byteBuffer, endDescriptionShift);
        writeD(byteBuffer, byteBuffer.position());

        writeD(byteBuffer, stockExchangeItem.getItemId());
        writeD(byteBuffer, stockExchangeItem.getAmount());
    }
}
