package com.angelis.tera.game.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.action.Action;
import com.angelis.tera.game.process.model.creature.AbstractCreature;
import com.angelis.tera.game.process.model.creature.Creature;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.model.visible.WorldPosition;

public class SM_ACTION_END extends TeraServerPacket {

    private final AbstractCreature creature;
    private final Action action;
    
    public SM_ACTION_END(final AbstractCreature creature, final Action action) {
        this.creature = creature;
        this.action = action;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        final WorldPosition worldPosition = this.creature.getWorldPosition();
        int model = 0;

        if (creature instanceof Player) {
            model = ((Player) creature).getRaceGenderClassValue();
        }
        else if (creature instanceof Creature) {
            model = creature.getId();
        }

        writeUid(byteBuffer, this.creature);
        writeWorldPosition(byteBuffer, worldPosition);
        writeD(byteBuffer, model);
        writeD(byteBuffer, this.action.getSkillId() + 0x4000000);
        writeD(byteBuffer, 0);
        writeD(byteBuffer, this.action.getUid());
    }
}
