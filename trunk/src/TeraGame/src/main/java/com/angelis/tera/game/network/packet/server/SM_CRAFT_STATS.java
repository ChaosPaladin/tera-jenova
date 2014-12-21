package com.angelis.tera.game.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.player.craft.Craft;
import com.angelis.tera.game.process.model.player.craft.CraftStats;

public class SM_CRAFT_STATS extends TeraServerPacket {

    private final CraftStats craftStats;
    
    public SM_CRAFT_STATS(final CraftStats craftStats) {
        this.craftStats = craftStats;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeH(byteBuffer, craftStats.getCrafts().size()); // craft skill counter?
        
        final int firstCraftSkillShift = byteBuffer.position();
        writeH(byteBuffer, 0);
        
        writeQ(byteBuffer, 0);
        writeB(byteBuffer, "00000048440E000000");

        this.writeBufferPosition(byteBuffer, firstCraftSkillShift);

        int i = 0;
        for (final Craft craftStatInfo : craftStats.getCrafts()) {
            writeH(byteBuffer, byteBuffer.position());
            
            final int nextCraftSkillShift = byteBuffer.position();
            writeH(byteBuffer, 0);
            
            writeD(byteBuffer, craftStatInfo.getCraftType().value);
            writeD(byteBuffer, craftStatInfo.getCraftType().value);
            writeD(byteBuffer, craftStatInfo.getLevel());
            
            if (++i < craftStats.getCrafts().size()) {
                this.writeBufferPosition(byteBuffer, nextCraftSkillShift);
            }
        }
    }

}
