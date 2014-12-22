package com.angelis.tera.game.presentation.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.creature.Creature;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.services.PlayerService;

public class SM_PLAYER_EXPERIENCE_UPDATE extends TeraServerPacket {

    private final Player player;
    private final Creature teraCreature;
    private final long added;
    
    public SM_PLAYER_EXPERIENCE_UPDATE(final Player player, final Creature teraCreature, final long added) {
        this.player = player;
        this.teraCreature = teraCreature;
        this.added = added;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        final int currentRestedExperience = this.player.getCurrentRestedExperience();
        final int maxRestedExperience = this.player.getMaxRestedExperience();
        
        float restedPercent = 0;
        if (currentRestedExperience > 0 && maxRestedExperience > 0) {
            restedPercent = currentRestedExperience/maxRestedExperience;
        }
        final int restExp = (int) (this.added*restedPercent/2);
        
        writeQ(byteBuffer, this.added+restExp);
        writeQ(byteBuffer, this.player.getExperience());
        writeQ(byteBuffer, PlayerService.getInstance().getExpShown(this.player));
        writeQ(byteBuffer, PlayerService.getInstance().getExpNeeded(this.player));
        writeUid(byteBuffer, this.teraCreature);

        /*new EXP params like death penalty or something else*/

        
        
        writeD(byteBuffer, 0);
        writeD(byteBuffer, restExp); 
        writeD(byteBuffer, currentRestedExperience);
        writeD(byteBuffer, maxRestedExperience);
        writeB(byteBuffer, "0000803F00000000");
    }
}
