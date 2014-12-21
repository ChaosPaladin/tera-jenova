package com.angelis.tera.game.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.group.Group;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.services.GroupService;

public class SM_GROUP_MEMBER_LIST extends TeraServerPacket {

    private final Group group;
    
    public SM_GROUP_MEMBER_LIST(final Group group) {
        this.group = group;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeH(byteBuffer, group.size());
        
        final int firstPartyShift = byteBuffer.position();
        writeH(byteBuffer, 0);
        
        writeH(byteBuffer, 0);
        writeB(byteBuffer, "05000000BA070000DE01");
        
        writeH(byteBuffer, GroupService.COMMON_GROUP_PACKET_VALUE);
        writeD(byteBuffer, GroupService.COMMON_GROUP_PACKET_VALUE);
        writeD(byteBuffer, group.getLeader().getId());
        
        writeD(byteBuffer, 1);
        writeD(byteBuffer, 2);
        writeB(byteBuffer, "0000010000000100000000");
        
        this.writeBufferPosition(byteBuffer, firstPartyShift);
        
        int i = 0;
        for (final Player player : group.getPlayers()) {
            writeH(byteBuffer, byteBuffer.position());
            
            final int nextPartyShift = byteBuffer.position();
            writeH(byteBuffer, 0);
            
            final int nameShift = byteBuffer.position();
            writeH(byteBuffer, 0);
            writeD(byteBuffer, 15);
            writeD(byteBuffer, player.getId());
            
            writeD(byteBuffer, player.getLevel());
            writeD(byteBuffer, player.getPlayerClass().value);
            writeC(byteBuffer, player.isOnline());
            writeUid(byteBuffer, player);
            writeD(byteBuffer, i); // position ?
            writeC(byteBuffer, 0);
            
            this.writeBufferPosition(byteBuffer, nameShift);
            
            writeS(byteBuffer, player.getName());
            if (++i < group.size()) {
                this.writeBufferPosition(byteBuffer, nextPartyShift);
            }
        }
    }

}
