package com.angelis.tera.game.presentation.network.packet.client;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraClientPacket;
import com.angelis.tera.game.process.services.PlayerService;

public class CM_PLAYER_REVIVE_DIALOG_CHOICE extends TeraClientPacket {

    private int choice;

    public CM_PLAYER_REVIVE_DIALOG_CHOICE(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        this.choice = readD();
        readD(); // unk 0xFFFFFFFF
    }

    @Override
    protected void runImpl() {
        PlayerService.getInstance().onPlayerRevive(this.getConnection().getActivePlayer(), this.choice);
    }

}
