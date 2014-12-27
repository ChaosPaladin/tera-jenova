package com.angelis.tera.game.presentation.network.packet.server.chat;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.group.LookingForGroupInfo;
import com.angelis.tera.game.process.model.player.Player;

public class SM_CHAT_LOOKING_FOR_GROUP extends TeraServerPacket {

    private final Player player;
    private final LookingForGroupInfo lookingForGroup;
    private final String message;
    
    public SM_CHAT_LOOKING_FOR_GROUP(final Player player, final LookingForGroupInfo lookingForGroup, final String message) {
        this.player = player;
        this.lookingForGroup = lookingForGroup;
        this.message = message;
    }
    
    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        
        final int playerNameShift = byteBuffer.position();
        writeH(byteBuffer, 0);
        
        final int messageShift = byteBuffer.position();
        writeH(byteBuffer, 0);
        
        writeD(byteBuffer, lookingForGroup.getUid());
        
        writeB(byteBuffer, "0000");
        
        writeBufferPosition(byteBuffer, playerNameShift);
        writeS(byteBuffer, player.getName());
        
        writeBufferPosition(byteBuffer, messageShift);
        writeS(byteBuffer, message);
    }
}
