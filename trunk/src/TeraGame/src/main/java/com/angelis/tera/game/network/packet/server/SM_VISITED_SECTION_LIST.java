package com.angelis.tera.game.network.packet.server;

import java.nio.ByteBuffer;
import java.util.Set;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.Zone;
import com.angelis.tera.game.process.model.player.Player;

public class SM_VISITED_SECTION_LIST extends TeraServerPacket {

    private final Player player;
    
    public SM_VISITED_SECTION_LIST(final Player player) {
        this.player = player;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        final Set<Zone> visitedZones = this.player.getVisitedZones();
        
        writeH(byteBuffer, visitedZones.size());
        final int firstVisitedZoneShift = byteBuffer.position();
        
        this.writeBufferPosition(byteBuffer, firstVisitedZoneShift);
        
        int i = 0;
        for (final Zone zone : visitedZones) {
            writeH(byteBuffer, byteBuffer.position());
            
            final int nextVisitedZoneShift = byteBuffer.position();
            writeH(byteBuffer, 0);
            
            writeB(byteBuffer, zone.getDatas());
            
            if (++i < visitedZones.size()) {
                this.writeBufferPosition(byteBuffer, nextVisitedZoneShift);
            }
        }
        
        // TODO
        writeB(byteBuffer, "020008000800180001000000020000000600000018000000010000000200000007000000");
    }
}
