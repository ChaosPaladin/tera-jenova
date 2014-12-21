package com.angelis.tera.game.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.creature.AbstractCreature;
import com.angelis.tera.game.process.model.creature.BaseStats;
import com.angelis.tera.game.process.model.creature.CurrentStats;

public class SM_CREATURE_SHOW_HP extends TeraServerPacket {

    private final AbstractCreature creature;
    private final boolean isFriendly;
    
    public SM_CREATURE_SHOW_HP(final AbstractCreature creature, final boolean isFriendly) {
        this.creature = creature;
        this.isFriendly = isFriendly;
    }
    
    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        final BaseStats baseStats = this.creature.getTemplate().getBaseStats();
        final CurrentStats currentStats = this.creature.getCurrentStats();
        
        writeUid(byteBuffer, this.creature);
        writeF(byteBuffer, (currentStats.getHp()/(baseStats.getBaseHp()/100F))/100F);
        writeC(byteBuffer, (byte) (this.isFriendly ? 0 : 1));
        writeQ(byteBuffer, 0);
        writeD(byteBuffer, 0x401F0000);
        writeD(byteBuffer, 0x03000000);
    }
}
