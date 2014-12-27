package com.angelis.tera.game.presentation.network.packet.server;

import java.nio.ByteBuffer;
import java.util.List;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.player.StockExchangeItem;

public class SM_STOCK_EXCHANGE_ITEM_ACCOUNT_LIST extends TeraServerPacket {

    private final List<StockExchangeItem> stockExchangeItems;
    
    public SM_STOCK_EXCHANGE_ITEM_ACCOUNT_LIST(final List<StockExchangeItem> stockExchangeItems) {
        this.stockExchangeItems = stockExchangeItems;
    }
    
    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeH(byteBuffer, stockExchangeItems.size());
        
        final int firstItemShift = byteBuffer.position();
        writeH(byteBuffer, 0);
        
        writeBufferPosition(byteBuffer, firstItemShift);
        
        int i = 0;
        for (final StockExchangeItem stockExchangeItem : stockExchangeItems) {
            // current shift
            writeH(byteBuffer, byteBuffer.position());
            
            // next shift
            final int nextItemShift = byteBuffer.position();
            writeH(byteBuffer, 0);
            
            writeB(byteBuffer, "13000000"); // ??? first is 13000000 second is 14000000 third is 17000000
            writeD(byteBuffer, stockExchangeItem.getItemId());
            
            if (i++ < stockExchangeItems.size()) {
                writeBufferPosition(byteBuffer, nextItemShift);
            }
        }
    }
}
