package com.angelis.tera.game.presentation.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.creature.Npc;
import com.angelis.tera.game.process.model.quest.Quest;

/***
 * This class is used to show dialog above npc
 */
public class SM_QUEST_BALLOON extends TeraServerPacket {

    private final Npc npc;
    private final Quest quest;
    
    public SM_QUEST_BALLOON(final Npc npc, final Quest quest) {
        this.npc = npc;
        this.quest = quest;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeB(byteBuffer, "0E00");
        writeUid(byteBuffer, this.npc);
        writeS(byteBuffer, "@quest:"+(this.quest.getId()*1000+2));
    }
}
