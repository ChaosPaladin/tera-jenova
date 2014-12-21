package com.angelis.tera.game.presentation.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.creature.AbstractCreature;
import com.angelis.tera.game.process.model.enums.DespawnTypeEnum;
import com.angelis.tera.game.process.model.visible.WorldPosition;

public class SM_CREATURE_DESPAWN extends TeraServerPacket {

    private final AbstractCreature creature;
    private final DespawnTypeEnum despawnType;

    public SM_CREATURE_DESPAWN(final AbstractCreature creature, final DespawnTypeEnum despawnType) {
        this.creature = creature;
        this.despawnType = despawnType;
    }
    
    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        final WorldPosition worldPosition = this.creature.getWorldPosition();
        
        writeUid(byteBuffer, this.creature);
        writeF(byteBuffer, worldPosition.getX());
        writeF(byteBuffer, worldPosition.getY());
        writeF(byteBuffer, worldPosition.getZ());
        writeD(byteBuffer, this.despawnType.value);
        writeD(byteBuffer, 0);
    }
}
