package com.angelis.tera.game.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.creature.Creature;

public class SM_DIALOG_CLOSE extends TeraServerPacket {

    private final Creature teraCreature;
    
    public SM_DIALOG_CLOSE(final Creature teraCreature) {
        this.teraCreature = teraCreature;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        this.writeUid(byteBuffer, this.teraCreature);
    }

}
