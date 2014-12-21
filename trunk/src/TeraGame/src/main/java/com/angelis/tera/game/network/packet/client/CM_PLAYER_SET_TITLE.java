package com.angelis.tera.game.network.packet.client;

import java.nio.ByteBuffer;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraClientPacket;
import com.angelis.tera.game.services.PlayerService;

public class CM_PLAYER_SET_TITLE extends TeraClientPacket {

    private int title;
    
    public CM_PLAYER_SET_TITLE(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        this.title = readD();
    }

    @Override
    protected void runImpl() {
        PlayerService.getInstance().onPlayerSetTitle(this.getConnection().getActivePlayer(), this.title);
    }

}
