package com.angelis.tera.game.presentation.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.model.storage.Storage;

public class SM_STORAGE extends TeraServerPacket {

    private final Player owner;
    private final Storage storage;
    
    public SM_STORAGE(final Player owner, final Storage storage) {
        this.owner = owner;
        this.storage = storage;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        final int shift1 = byteBuffer.position();
        writeH(byteBuffer, 67);
        
        final int shift2 = byteBuffer.position();
        writeH(byteBuffer, 0);
        
        writeUid(byteBuffer, this.owner);
        
        writeD(byteBuffer, 1);
        writeQ(byteBuffer, 0);
        writeD(byteBuffer, 66);
        writeD(byteBuffer, 67);
        writeQ(byteBuffer, this.storage.getMoney());
        writeH(byteBuffer, this.storage.getSize());
    }
}
