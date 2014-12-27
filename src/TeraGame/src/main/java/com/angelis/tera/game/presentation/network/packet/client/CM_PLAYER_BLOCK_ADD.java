package com.angelis.tera.game.presentation.network.packet.client;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraClientPacket;
import com.angelis.tera.game.process.services.PlayerService;

public class CM_PLAYER_BLOCK_ADD extends TeraClientPacket {

    private String playerName;
    
    public CM_PLAYER_BLOCK_ADD(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        readH();
        this.playerName = readS();
    }

    @Override
    protected void runImpl() {
        PlayerService.getInstance().onPlayerBlock(this.getConnection().getActivePlayer(), this.playerName);
    }
}
