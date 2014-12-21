package com.angelis.tera.game.presentation.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.creature.AbstractCreature;
import com.angelis.tera.game.process.model.creature.BaseStats;
import com.angelis.tera.game.process.model.creature.CurrentStats;

public class SM_PLAYER_SELECT_CREATURE extends TeraServerPacket {

    private final AbstractCreature creature;
    
    public SM_PLAYER_SELECT_CREATURE(final AbstractCreature creature) {
        this.creature = creature;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        final CurrentStats currentStats = this.creature.getCurrentStats();
        final BaseStats baseStats = this.creature.getTemplate().getBaseStats();
        
        writeH(byteBuffer, 0); // TODO (short) Creature.Effects.Count
        byteBuffer.position();
        
        writeH(byteBuffer, 0); //first Abnormal shift
        writeD(byteBuffer, 0); //unk
        writeUid(byteBuffer, this.creature);
        writeF(byteBuffer, (currentStats.getHp()/(baseStats.getBaseHp()/100F))/100F);
        writeD(byteBuffer, 0);
        writeD(byteBuffer, creature.getLevel()); //npc level
        
        writeQ(byteBuffer, 0);
        writeQ(byteBuffer, 0);
        writeB(byteBuffer, "401F000003000000"); // ololo o_O

        // TODO
        /*for (int i = 0; i < Creature.Effects.Count; i++)
        {
            byteBuffer.Seek(effectShift, SeekOrigin.Begin);
            writeH(byteBuffer, (short) byteBuffer.BaseStream.Length);
            byteBuffer.Seek(0, SeekOrigin.End);

            writeH(byteBuffer, (short) byteBuffer.BaseStream.Length);
            effectShift = (int) byteBuffer.BaseStream.Position;
            writeH(byteBuffer, 0); //posible next Abnormal shift
            writeD(byteBuffer, Creature.Effects[i].Abnormality.Id);
            writeD(byteBuffer, Creature.Effects[i].TimeRemain);
            writeD(byteBuffer, 1);
        }*/
    }
}
