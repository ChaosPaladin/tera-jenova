package com.angelis.tera.game.presentation.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.creature.Npc;
import com.angelis.tera.game.process.model.quest.enums.QuestNpcIconEnum;

public class SM_QUEST_WORLD_VILLAGER_INFO extends TeraServerPacket {

    private final Npc npc;
    private final QuestNpcIconEnum questNpcIcon;
    
    public SM_QUEST_WORLD_VILLAGER_INFO(final Npc npc, final QuestNpcIconEnum questNpcIcon) {
        this.npc = npc;
        this.questNpcIcon = questNpcIcon;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeH(byteBuffer, 1);
        writeH(byteBuffer, 8); // first shift
        
        writeH(byteBuffer, 8); // this shift
        writeH(byteBuffer, 0);
        writeD(byteBuffer, questNpcIcon.value);
        writeB(byteBuffer, connection.getActivePlayer().getCurrentZoneData());
        writeWorldPosition(byteBuffer, npc.getWorldPosition());
        
        writeH(byteBuffer, 0); // next shift
    }

}
