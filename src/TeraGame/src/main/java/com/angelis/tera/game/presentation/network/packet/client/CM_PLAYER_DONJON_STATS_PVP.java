package com.angelis.tera.game.presentation.network.packet.client;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraClientPacket;
import com.angelis.tera.game.presentation.network.packet.server.player.SM_PLAYER_DONJON_STATS_PVP;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.services.WorldService;

public class CM_PLAYER_DONJON_STATS_PVP extends TeraClientPacket {

    private String playerName;
    
    public CM_PLAYER_DONJON_STATS_PVP(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        this.playerName = readS();
    }

    @Override
    protected void runImpl() {
        final Player player = WorldService.getInstance().getOnlinePlayerByName(playerName);
        if (player != null) {
            this.getConnection().sendPacket(new SM_PLAYER_DONJON_STATS_PVP(player));
        }
    }

}
