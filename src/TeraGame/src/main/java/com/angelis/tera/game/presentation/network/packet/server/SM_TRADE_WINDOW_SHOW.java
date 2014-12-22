package com.angelis.tera.game.presentation.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.player.request.PlayerTradeRequest;

public class SM_TRADE_WINDOW_SHOW extends TeraServerPacket {

    private final PlayerTradeRequest request;
    
    public SM_TRADE_WINDOW_SHOW(final PlayerTradeRequest request) {
        this.request = request;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeD(byteBuffer, 56);
        writeD(byteBuffer, 56);
        writeUid(byteBuffer, this.request.getInitiator());
        writeUid(byteBuffer, this.request.getTarget());
        writeD(byteBuffer, this.request.getUid());
        writeQ(byteBuffer, 0); // what
        writeQ(byteBuffer, 0); // the
        writeQ(byteBuffer, 0); // hell ?
    }

}
