package com.angelis.tera.game.presentation.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.creature.Creature;
import com.angelis.tera.game.process.model.visible.WorldPosition;


public class SM_CREATURE_MOVE extends TeraServerPacket {
    private final Creature teraCreature;
    private final float x;
    private final float y;
    private final float z;
    
    public SM_CREATURE_MOVE(final Creature teraCreature, final float x, final float y, final float z) {
        this.teraCreature = teraCreature;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        final WorldPosition worldPosition = this.teraCreature.getWorldPosition();
        
        writeUid(byteBuffer, this.teraCreature);
        writeF(byteBuffer, worldPosition.getX());
        writeF(byteBuffer, worldPosition.getY());
        writeF(byteBuffer, worldPosition.getZ());
        
        writeH(byteBuffer, worldPosition.getHeading());
        writeH(byteBuffer, this.teraCreature.getCurrentStats().getSpeed());
        
        writeF(byteBuffer, x);
        writeF(byteBuffer, y);
        writeF(byteBuffer, z);
        writeD(byteBuffer, 0);
    }

}
