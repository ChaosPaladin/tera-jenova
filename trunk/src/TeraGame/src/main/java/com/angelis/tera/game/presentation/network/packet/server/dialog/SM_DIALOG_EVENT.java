package com.angelis.tera.game.presentation.network.packet.server.dialog;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.creature.AbstractCreature;
import com.angelis.tera.game.process.model.creature.Creature;

public class SM_DIALOG_EVENT extends TeraServerPacket {

    private final Creature teraCreature;
    private final AbstractCreature target;
    private final int page;
    
    public SM_DIALOG_EVENT(final Creature teraCreature, final AbstractCreature target, final int page) {
        this.teraCreature = teraCreature;
        this.target = target;
        this.page = page;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeUid(byteBuffer, this.teraCreature);
        writeUid(byteBuffer, this.target);

        writeD(byteBuffer, page); // this can be 0, 1, 2, 3, 4 or 5
        writeD(byteBuffer, 1);
        writeD(byteBuffer, 0);
    }
}
