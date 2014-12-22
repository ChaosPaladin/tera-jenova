package com.angelis.tera.game.presentation.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.model.player.request.AbstractRequest;

public class SM_REQUEST_WINDOW_SHOW extends TeraServerPacket {

    private final AbstractRequest<Player, Player> request;
    
    public SM_REQUEST_WINDOW_SHOW(final AbstractRequest<Player, Player> request) {
        this.request = request;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        final int initiatorNameShift = byteBuffer.position();
        writeH(byteBuffer, 0);

        final int targetNameShift = byteBuffer.position();
        writeH(byteBuffer, 0);

        writeH(byteBuffer, 15);
        
        writeD(byteBuffer, this.request.getRequestType().value);
        writeD(byteBuffer, this.request.getUid());
        writeB(byteBuffer, "C5270000");

        this.writeBufferPosition(byteBuffer, initiatorNameShift);
        writeS(byteBuffer, this.request.getInitiator().getName());

        this.writeBufferPosition(byteBuffer, targetNameShift);
        writeS(byteBuffer, this.request.getTarget().getName());
    }

}
