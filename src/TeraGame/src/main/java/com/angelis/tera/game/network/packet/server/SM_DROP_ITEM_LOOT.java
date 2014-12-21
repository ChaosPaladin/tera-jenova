package com.angelis.tera.game.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.drop.DropItem;
import com.angelis.tera.game.process.model.player.Player;

public class SM_DROP_ITEM_LOOT extends TeraServerPacket {

    private final Player player;
    private final DropItem dropItem;
    private final int amount;
    
    public SM_DROP_ITEM_LOOT(final Player player, final DropItem dropItem, final int amount) {
        this.player = player;
        this.dropItem = dropItem;
        this.amount = amount;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeUid(byteBuffer, this.player);
        writeD(byteBuffer, 0);
        writeUid(byteBuffer, this.dropItem);
        writeC(byteBuffer, this.amount);
    }
}
