package com.angelis.tera.game.presentation.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.common.utils.Point3D;
import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.creature.AbstractCreature;

public class SM_CREATURE_INSTANCE_ARROW extends TeraServerPacket {

    private final AbstractCreature creature;
    private final int skillId;
    private final Point3D point3D;

    public SM_CREATURE_INSTANCE_ARROW(final AbstractCreature creature, final Point3D worldPosition, final int skillId) {
        this.creature = creature;
        this.point3D = worldPosition;
        this.skillId = skillId;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeD(byteBuffer, 0);
        writeH(byteBuffer, 1);
        writeH(byteBuffer, 2);

        writeUid(byteBuffer, this.creature);
        writeB(byteBuffer, "002B0000");
        writeD(byteBuffer, this.skillId + 0x4000000);

        writeB(byteBuffer, "6117120620000000");

        writeF(byteBuffer, point3D.getX());
        writeF(byteBuffer, point3D.getY());
        writeF(byteBuffer, point3D.getZ());
    }

}
