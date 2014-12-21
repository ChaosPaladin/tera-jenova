package com.angelis.tera.game.presentation.network.packet.client;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraClientPacket;
import com.angelis.tera.game.process.services.PlayerService;

/**
 * @author Angelis
 *
 * This packet is sent by client when you click on "Quit game" button.
 */
public class CM_QUIT_GAME extends TeraClientPacket {

    public CM_QUIT_GAME(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        // Empty packet
    }

    @Override
    protected void runImpl() {
        PlayerService.getInstance().onPlayerQuit(this.getConnection().getActivePlayer(), true);
    }
}
