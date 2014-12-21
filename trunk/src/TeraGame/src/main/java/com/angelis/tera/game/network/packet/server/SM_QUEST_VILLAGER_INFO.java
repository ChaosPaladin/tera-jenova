package com.angelis.tera.game.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.creature.AbstractCreature;
import com.angelis.tera.game.process.model.quest.enums.QuestNpcIconEnum;

public class SM_QUEST_VILLAGER_INFO extends TeraServerPacket {

    private final AbstractCreature creature;
    private final QuestNpcIconEnum questNpcIcon;
    
    public SM_QUEST_VILLAGER_INFO(final AbstractCreature creature, final QuestNpcIconEnum questNpcIcon) {
        this.creature = creature;
        this.questNpcIcon = questNpcIcon;
    }   

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeUid(byteBuffer, this.creature);
        writeD(byteBuffer, this.questNpcIcon.value);
        writeC(byteBuffer, 1);
    }
}
