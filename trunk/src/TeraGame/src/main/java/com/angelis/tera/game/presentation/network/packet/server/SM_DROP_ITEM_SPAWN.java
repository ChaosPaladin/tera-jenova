package com.angelis.tera.game.presentation.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.drop.DropItem;
import com.angelis.tera.game.process.model.visible.WorldPosition;

public class SM_DROP_ITEM_SPAWN extends TeraServerPacket {

    private final DropItem dropItem;
    
    public SM_DROP_ITEM_SPAWN(final DropItem dropItem) {
        this.dropItem = dropItem;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        final WorldPosition worldPosition = dropItem.getWorldPosition();
        
        writeB(byteBuffer, "01003600"); //Shifts
        writeUid(byteBuffer, this.dropItem);
        writeF(byteBuffer, worldPosition.getX());
        writeF(byteBuffer, worldPosition.getY());
        writeF(byteBuffer, worldPosition.getZ());
        writeD(byteBuffer, this.dropItem.getItem().getId());
        writeD(byteBuffer, this.dropItem.getAmount());
        writeB(byteBuffer, "B0D40100010000000000"); //??? 57
        writeUid(byteBuffer, this.dropItem.getCreature());
        writeB(byteBuffer, "36000000"); //Shifts
        writeUid(byteBuffer, this.dropItem.getOwner());
    }

}
