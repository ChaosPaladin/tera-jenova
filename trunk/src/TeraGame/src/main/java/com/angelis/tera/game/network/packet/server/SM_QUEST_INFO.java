package com.angelis.tera.game.network.packet.server;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map.Entry;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.creature.Npc;
import com.angelis.tera.game.process.model.quest.Quest;
import com.angelis.tera.game.process.model.quest.QuestEnv;

public class SM_QUEST_INFO extends TeraServerPacket {

    private final QuestEnv questEnv;
    private final List<Npc> npcs;
    private final boolean countersComplete;
    
    public SM_QUEST_INFO(final QuestEnv questEnv, final List<Npc> npcs, final boolean countersComplete) {
        this.questEnv = questEnv;
        this.npcs = npcs;
        this.countersComplete = countersComplete;
    }
    
    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        final Quest quest = this.questEnv.getQuest();

        writeB(byteBuffer, "01000E000C00000100000E000000");

        writeH(byteBuffer, this.npcs.size());
        final int npcShift = byteBuffer.position();
        writeH(byteBuffer, 0);

        writeH(byteBuffer, this.questEnv.getCounters().size());
        final int countersShift = byteBuffer.position();
        writeH(byteBuffer, 0);

        writeD(byteBuffer, quest.getId());
        writeB(byteBuffer, "E35B9603"); //QuestUId???
        writeD(byteBuffer, this.questEnv.getCurrentStep() + 1);
        writeD(byteBuffer, 1);
        writeD(byteBuffer, 0);
        writeD(byteBuffer, 1); // visibility switch

        writeC(byteBuffer, 0);
        writeC(byteBuffer, this.countersComplete);
        writeC(byteBuffer, 1);

        writeD(byteBuffer, 0);
        
        writeB(byteBuffer, "000000000000000000000000");

        this.writeBufferPosition(byteBuffer, npcShift);
        int i = 0;
        for (final Npc npc : this.npcs) {
            writeH(byteBuffer, byteBuffer.position());
            final int nextNpcShift = byteBuffer.position();
            writeH(byteBuffer, 0);

            writeF(byteBuffer, npc.getWorldPosition().getX());
            writeF(byteBuffer, npc.getWorldPosition().getY());
            writeF(byteBuffer, npc.getWorldPosition().getZ());
            writeD(byteBuffer, 0); // TODO
            writeD(byteBuffer, npc.getId());
            writeD(byteBuffer, npc.getWorldPosition().getMapId()); // not sure

            if (++i < this.npcs.size()) {
                this.writeBufferPosition(byteBuffer, nextNpcShift);
            }
        }

        this.writeBufferPosition(byteBuffer, countersShift);
        int j = 0;
        for (final Entry<Integer, Integer> entry : this.questEnv.getCounters().entrySet()) {
            writeH(byteBuffer, byteBuffer.position());
            final int nextCountersShift = byteBuffer.position();
            writeH(byteBuffer, 0);

            writeD(byteBuffer, entry.getValue());

            if (++j < this.questEnv.getCounters().size()) {
                this.writeBufferPosition(byteBuffer, nextCountersShift);
            }
        }
    }
}
