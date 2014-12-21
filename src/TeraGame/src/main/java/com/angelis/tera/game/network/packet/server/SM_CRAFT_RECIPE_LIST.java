package com.angelis.tera.game.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.player.Player;

public class SM_CRAFT_RECIPE_LIST extends TeraServerPacket {

    private final Player player;
    
    public SM_CRAFT_RECIPE_LIST(final Player player) {
        this.player = player;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        // TODO
        writeB(byteBuffer, "000000000001");
    }
}
