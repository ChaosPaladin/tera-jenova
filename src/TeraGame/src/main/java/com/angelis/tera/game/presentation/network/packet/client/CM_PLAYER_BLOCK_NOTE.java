package com.angelis.tera.game.presentation.network.packet.client;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraClientPacket;
import com.angelis.tera.game.process.services.PlayerService;

public class CM_PLAYER_BLOCK_NOTE extends TeraClientPacket {

    private int playerId;
    private String note;

    public CM_PLAYER_BLOCK_NOTE(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        readH(); // unk 0A00
        this.playerId = readD();
        this.note = readS();
    }

    @Override
    protected void runImpl() {
        PlayerService.getInstance().onPlayerBlockNote(this.getConnection().getActivePlayer(), this.playerId, this.note);
    }

}
