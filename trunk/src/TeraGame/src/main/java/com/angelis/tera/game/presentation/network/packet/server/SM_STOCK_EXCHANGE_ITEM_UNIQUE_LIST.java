package com.angelis.tera.game.presentation.network.packet.server;

import java.nio.ByteBuffer;
import java.util.List;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.player.StockExchangeItem;

public class SM_STOCK_EXCHANGE_ITEM_UNIQUE_LIST extends TeraServerPacket {

    private final List<StockExchangeItem> stockExchangeItems;

    public SM_STOCK_EXCHANGE_ITEM_UNIQUE_LIST(final List<StockExchangeItem> stockExchangeItems) {
        this.stockExchangeItems = stockExchangeItems;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeH(byteBuffer, stockExchangeItems.size());

        final int firstItemShift = byteBuffer.position();
        writeH(byteBuffer, 0);

        writeB(byteBuffer, "000000000000000000");

        writeBufferPosition(byteBuffer, firstItemShift);

        int i = 0;
        for (final StockExchangeItem stockExchangeItem : stockExchangeItems) {
            // current shift
            writeH(byteBuffer, byteBuffer.position());

            // next shift
            final int nextItemShift = byteBuffer.position();
            writeH(byteBuffer, 0);

            final int titleShift = byteBuffer.position();
            writeH(byteBuffer, 0);

            final int imageShift = byteBuffer.position();
            writeH(byteBuffer, 0);

            writeUid(byteBuffer, stockExchangeItem);

            writeQ(byteBuffer, stockExchangeItem.getReceptionDate().getTime() / 1000);
            writeQ(byteBuffer, stockExchangeItem.getExpirationDate().getTime() / 1000);

            writeB(byteBuffer, "F60E231E000000000200000000"); // seem to be static

            writeBufferPosition(byteBuffer, titleShift);
            writeS(byteBuffer, stockExchangeItem.getTitle());

            writeBufferPosition(byteBuffer, imageShift);
            writeS(byteBuffer, "Giftbox01.bmp");

            if (i++ < stockExchangeItems.size()) {
                writeBufferPosition(byteBuffer, nextItemShift);
            }
        }
    }
}
