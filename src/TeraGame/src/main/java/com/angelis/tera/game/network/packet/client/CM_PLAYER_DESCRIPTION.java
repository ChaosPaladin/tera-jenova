package com.angelis.tera.game.network.packet.client;

import java.nio.ByteBuffer;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraClientPacket;

public class CM_PLAYER_DESCRIPTION extends TeraClientPacket {

    private String description;

    public CM_PLAYER_DESCRIPTION(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        readH();
        this.description = readS();
    }

    @Override
    protected void runImpl() {
        this.getConnection().getActivePlayer().setDescription(this.description);
    }

}
