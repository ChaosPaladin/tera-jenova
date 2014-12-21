package com.angelis.tera.game.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.abnormality.Abnormality;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.services.GroupService;

public class SM_GROUP_ABNORMALS extends TeraServerPacket {

    private final Player player;
    
    public SM_GROUP_ABNORMALS(final Player player) {
        this.player = player;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        final int abnormalitySize = player.getAbnormalities().size();

        writeD(byteBuffer, 0);
        writeH(byteBuffer, abnormalitySize); // abnormals counts
        
        final int firstAbnormalShift = byteBuffer.position();
        writeH(byteBuffer, 0);
        
        writeD(byteBuffer, GroupService.COMMON_GROUP_PACKET_VALUE);
        writeD(byteBuffer, this.player.getId());

        this.writeBufferPosition(byteBuffer, firstAbnormalShift);
        
        int i = 0;
        for (final Abnormality abnormality : player.getAbnormalities()) {
            writeH(byteBuffer, byteBuffer.position());
            
            final int nextAbnormalityShift = byteBuffer.position();
            writeH(byteBuffer, 0);
            
            writeD(byteBuffer, abnormality.getId()); // not sure
            writeD(byteBuffer, 0);
            writeC(byteBuffer, 1);
            
            if (++i < abnormalitySize) {
                this.writeBufferPosition(byteBuffer, nextAbnormalityShift);
            }
        }
    }

}
