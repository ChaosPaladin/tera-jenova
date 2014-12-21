package com.angelis.tera.game.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.creature.AbstractCreature;

public class SM_CREATURE_MP_UPDATE extends TeraServerPacket {

    private final AbstractCreature creature;
    private final AbstractCreature attacker;
    private final int diff;
    
    public SM_CREATURE_MP_UPDATE(final AbstractCreature creature, final AbstractCreature attacker, final int diff) {
        this.creature = creature;
        this.attacker = attacker;
        this.diff = diff;
    }
    
    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeD(byteBuffer, creature.getCurrentStats().getMp());
        writeD(byteBuffer, creature.getTemplate().getBaseStats().getBaseMp());
        writeD(byteBuffer, this.diff);

        writeD(byteBuffer, attacker != null ? 0 : 3);
        writeUid(byteBuffer, creature);
        writeUid(byteBuffer, attacker);
    }
}
