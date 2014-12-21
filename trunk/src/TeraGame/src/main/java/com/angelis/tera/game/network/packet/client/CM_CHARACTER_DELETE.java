package com.angelis.tera.game.network.packet.client;

import java.nio.ByteBuffer;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraClientPacket;
import com.angelis.tera.game.network.packet.server.SM_CHARACTER_DELETE;
import com.angelis.tera.game.services.PlayerService;

public class CM_CHARACTER_DELETE extends TeraClientPacket {

    private int playerId;
    
    public CM_CHARACTER_DELETE(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        this.playerId = readD();
    }

    @Override
    protected void runImpl() {
        PlayerService.getInstance().deletePlayer(playerId);
        this.getConnection().sendPacket(new SM_CHARACTER_DELETE(true));
    }

}
