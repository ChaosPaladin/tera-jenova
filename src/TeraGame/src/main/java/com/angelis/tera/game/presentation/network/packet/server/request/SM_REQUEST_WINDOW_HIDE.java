package com.angelis.tera.game.presentation.network.packet.server.request;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.model.player.request.AbstractRequest;

public class SM_REQUEST_WINDOW_HIDE extends TeraServerPacket {

    private final AbstractRequest<Player, ?> request;
    
    public SM_REQUEST_WINDOW_HIDE(final AbstractRequest<Player, ?> request) {
        this.request = request;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeH(byteBuffer, 15);
        writeD(byteBuffer, this.request.getRequestType().value);
        writeD(byteBuffer, this.request.getUid());
        writeS(byteBuffer, this.request.getInitiator().getName());
    }

}
