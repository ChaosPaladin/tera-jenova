package com.angelis.tera.game.presentation.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.creature.Creature;
import com.angelis.tera.game.process.model.creature.CurrentStats;
import com.angelis.tera.game.process.model.template.CreatureTemplate;
import com.angelis.tera.game.process.model.visible.WorldPosition;

public class SM_CREATURE_SPAWN extends TeraServerPacket {

    public final Creature teraCreature;
    
    public SM_CREATURE_SPAWN(final Creature teraCreature) {
        this.teraCreature = teraCreature;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        final WorldPosition worldPosition = this.teraCreature.getWorldPosition();
        final CurrentStats currentStats = this.teraCreature.getCurrentStats();
        final CreatureTemplate template = this.teraCreature.getTemplate();

        writeD(byteBuffer, 0); //???
        writeH(byteBuffer, 0); //Shit shift

        writeUid(byteBuffer, this.teraCreature);
        writeUid(byteBuffer, this.teraCreature.getTarget());

        writeF(byteBuffer, worldPosition.getX());
        writeF(byteBuffer, worldPosition.getY());
        writeF(byteBuffer, worldPosition.getZ());
        writeH(byteBuffer, worldPosition.getHeading());
        writeD(byteBuffer, currentStats.getSpeed());
        writeD(byteBuffer, this.teraCreature.getId());
        writeH(byteBuffer, template.getHuntingZoneId());
        writeD(byteBuffer, template.getModelId());

        writeB(byteBuffer, "000000000500000001010100000000000000000000000000000000000000");

        writeUid(byteBuffer, this.teraCreature.getTarget());

        writeB(byteBuffer, "00000000000000000000000000000000000000000000");

        this.writeBufferPosition(byteBuffer, 8);

        writeB(byteBuffer, "D8C5ACB974CE0000"); //Shit
    }
}
