package com.angelis.tera.game.presentation.network.packet.server;

import java.nio.ByteBuffer;
import java.util.Map.Entry;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.player.request.NpcTradeRequest;

public class SM_EXCHANGE_WINDOW_UPDATE extends TeraServerPacket {

    private final NpcTradeRequest exchangeRequest;
    
    public SM_EXCHANGE_WINDOW_UPDATE(final NpcTradeRequest exchangeRequest) {
        this.exchangeRequest = exchangeRequest;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeH(byteBuffer, this.exchangeRequest.getBuyItems().size());
        final int firstBuyItemShift = byteBuffer.position();
        writeH(byteBuffer, 0);
        
        writeH(byteBuffer, this.exchangeRequest.getSellItems().size());
        final int firstSellItemShift = byteBuffer.position();
        writeH(byteBuffer, 0);
        
        writeUid(byteBuffer, this.exchangeRequest.getInitiator());
        writeD(byteBuffer, this.exchangeRequest.getUid());
        
        writeQ(byteBuffer, 0); // maybe money
        
        writeQ(byteBuffer, 0); // TODO buy money
        
        writeB(byteBuffer, "00000020"); // this is dialogstring
        writeB(byteBuffer, "85EBB13F");
        
        writeQ(byteBuffer, 0); // TODO sell money
        
        if (this.exchangeRequest.getBuyItems().size() > 0) {
            this.writeBufferPosition(byteBuffer, firstBuyItemShift);
            int buyCount = 0;
            for (final Entry<Integer, Integer> entry : this.exchangeRequest.getBuyItems().entrySet()) {
                writeH(byteBuffer, byteBuffer.position());
                
                final int nextBuyItemShift = byteBuffer.position();
                writeH(byteBuffer, 0);
                
                writeD(byteBuffer, entry.getKey()); // itemId
                writeD(byteBuffer, entry.getValue());// itemAmount
                
                if (++buyCount < this.exchangeRequest.getBuyItems().size()) {
                    this.writeBufferPosition(byteBuffer, nextBuyItemShift);
                }
            }
        }
        
        if (this.exchangeRequest.getSellItems().size() > 0) {
            this.writeBufferPosition(byteBuffer, firstSellItemShift);
            int sellCount = 0;
            for (final Entry<Integer, Integer> entry : this.exchangeRequest.getSellItems().entrySet()) {
                writeH(byteBuffer, byteBuffer.position());
                
                final int nextSellItemShift = byteBuffer.position();
                writeH(byteBuffer, 0);
                
                writeD(byteBuffer, entry.getKey()); // itemId
                writeD(byteBuffer, entry.getValue());// itemAmount
                
                if (++sellCount < this.exchangeRequest.getSellItems().size()) {
                    this.writeBufferPosition(byteBuffer, nextSellItemShift);
                }
            }
            writeQ(byteBuffer, 0);
            writeH(byteBuffer, 0);
        }
    }

}
