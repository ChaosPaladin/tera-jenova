package com.angelis.tera.game.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.action.Action;
import com.angelis.tera.game.process.model.creature.AbstractCreature;
import com.angelis.tera.game.process.model.player.Player;

public class SM_ACTION_STAGE extends TeraServerPacket {

    private final AbstractCreature creature;
    private final Action action;
    
    public SM_ACTION_STAGE(final AbstractCreature creature, final Action action) {
        this.creature = creature;
        this.action = action;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        int model = 0;
        
        if (this.creature instanceof Player) {
            model = ((Player) this.creature).getRaceGenderClassValue();
        }
        else if (this.creature instanceof AbstractCreature) {
            model = creature.getId();
        }
        
        if (action.getStage() == 0) {
            writeD(byteBuffer, 0);
        }
        else {
            writeH(byteBuffer, 1); //Unk count
            writeH(byteBuffer, 50); //Shift
        }
        
        writeUid(byteBuffer, this.creature);
        writeWorldPosition(byteBuffer, this.action.getStartPosition());
        writeD(byteBuffer, model);
        writeD(byteBuffer, this.action.getSkillId() + 0x4000000);
        writeD(byteBuffer, this.action.getStage());
        writeB(byteBuffer, "0000803F"); // ATTACK SPEED ???
        
        if (this.creature instanceof Player) {
            writeD(byteBuffer, this.action.getUid());
        }
        else {
            writeD(byteBuffer, 0);
        }
        
        if (this.action.getStage() == 0) {
            writeB(byteBuffer, "0000803F");
        }
    }
}
