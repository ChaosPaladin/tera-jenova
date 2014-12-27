package com.angelis.tera.game.presentation.network.packet.server.chat;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.group.Group;
import com.angelis.tera.game.process.model.group.LookingForGroupInfo;

public class SM_CHAT_LOOKING_FOR_GROUP_INFO extends TeraServerPacket {

    private final LookingForGroupInfo lookingForGroupInfo;

    public SM_CHAT_LOOKING_FOR_GROUP_INFO(final LookingForGroupInfo lookingForGroupInfo) {
        this.lookingForGroupInfo = lookingForGroupInfo;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        final Group group = this.lookingForGroupInfo.getGroup();

        writeH(byteBuffer, group.size());
        writeB(byteBuffer, "0A0000000A003800"); // maybe shifts or target
        
        final int leaderNameShift = byteBuffer.position();
        writeH(byteBuffer, 0);
        
        writeD(byteBuffer, this.lookingForGroupInfo.getUid());
        
        writeB(byteBuffer, "04000300000041000100000018000000E19F020001010000"); // maybe shifts or target
        
        writeBufferPosition(byteBuffer, leaderNameShift);
        writeS(byteBuffer, group.getLeader().getName());
        
        // TODO 
        // some info aboute player
        
        // TODO
        // loop on other player
    }
}
