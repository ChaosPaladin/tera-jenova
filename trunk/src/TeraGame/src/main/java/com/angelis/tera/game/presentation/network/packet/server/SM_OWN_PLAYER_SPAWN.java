package com.angelis.tera.game.presentation.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.model.visible.WorldPosition;

public class SM_OWN_PLAYER_SPAWN extends TeraServerPacket {

    private final Player player;
    
    public SM_OWN_PLAYER_SPAWN(final Player player) {
        this.player = player;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        final WorldPosition worldPosition = this.player.getWorldPosition();

        writeUid(byteBuffer, this.player);
        
        writeF(byteBuffer, worldPosition.getX());
        writeF(byteBuffer, worldPosition.getY());
        writeF(byteBuffer, worldPosition.getZ());
        writeH(byteBuffer, worldPosition.getHeading());
        writeH(byteBuffer, this.player.getCurrentStats().isDead() ? 0 : 1);
    }
}
