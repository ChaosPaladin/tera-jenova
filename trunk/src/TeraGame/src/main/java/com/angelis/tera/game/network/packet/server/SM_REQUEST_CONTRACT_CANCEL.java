package com.angelis.tera.game.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.player.request.AbstractRequest;

public class SM_REQUEST_CONTRACT_CANCEL extends TeraServerPacket {

    private final AbstractRequest<?, ?> request;
    
    public SM_REQUEST_CONTRACT_CANCEL(final AbstractRequest<?, ?> request) {
        this.request = request;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeUid(byteBuffer, this.request.getInitiator());
        writeQ(byteBuffer, 0);
        writeD(byteBuffer, this.request.getRequestType().value);
    }

}
