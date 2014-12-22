package com.angelis.tera.game.presentation.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.drop.DropItem;

public class SM_DROP_ITEM_DESPAWN extends TeraServerPacket {

    private final DropItem dropItem;
    
    public SM_DROP_ITEM_DESPAWN(final DropItem dropItem) {
        this.dropItem = dropItem;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeUid(byteBuffer, this.dropItem);
    }

}
