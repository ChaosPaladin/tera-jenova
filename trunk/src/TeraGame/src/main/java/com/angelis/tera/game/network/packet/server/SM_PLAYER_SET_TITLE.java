package com.angelis.tera.game.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.player.Player;

public class SM_PLAYER_SET_TITLE extends TeraServerPacket {

    private final Player player;
    private final int title;
    
    public SM_PLAYER_SET_TITLE(final Player player, final int title) {
        super();
        this.player = player;
        this.title = title;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeUid(byteBuffer, this.player); // TO CHECK
        writeD(byteBuffer, this.title);
        writeD(byteBuffer, 0); // UNK TIME LEFT
        writeD(byteBuffer, 0); // UNK TIME MAX
    }

}
