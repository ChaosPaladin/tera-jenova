package com.angelis.tera.game.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.creature.AbstractCreature;

public class SM_CREATURE_UNK extends TeraServerPacket {

    private final AbstractCreature creature;
    
    public SM_CREATURE_UNK(final AbstractCreature creature) {
        this.creature = creature;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeUid(byteBuffer, this.creature);
        writeD(byteBuffer, 0);
        writeC(byteBuffer, 1);
    }

}
