package com.angelis.tera.game.presentation.network.packet.server.request;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.player.request.enums.RequestTypeEnum;

public class SM_REQUEST_CONTRACT_REPLY extends TeraServerPacket {

    private final RequestTypeEnum requestType;
    
    public SM_REQUEST_CONTRACT_REPLY(final RequestTypeEnum requestType) {
        this.requestType = requestType;
    }
    
    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeD(byteBuffer, this.requestType.value);
    }
}
