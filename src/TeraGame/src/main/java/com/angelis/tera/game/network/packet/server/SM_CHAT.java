package com.angelis.tera.game.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.channel.enums.ChatTypeEnum;
import com.angelis.tera.game.process.model.player.Player;

public class SM_CHAT extends TeraServerPacket {

    private final Player player;
    private final String message;
    private final ChatTypeEnum chatType;
    
    public SM_CHAT(final Player player, final String message, final ChatTypeEnum chatType) {
        this.player = player;
        this.message = message;
        this.chatType = chatType;
    }
    
    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        final short senderShift = (short) byteBuffer.position();
        writeH(byteBuffer, 0);
        
        final short messageShift = (short) byteBuffer.position();
        writeH(byteBuffer, 0);

        writeD(byteBuffer, this.chatType.getValue());

        writeUid(byteBuffer, this.player);

        writeH(byteBuffer, 0); //Blue shit
        writeC(byteBuffer, player != null ? player.getAccount().getAccess() > 0 : false); //GM

        if (this.player != null) {
            this.writeBufferPosition(byteBuffer, senderShift);
            writeS(byteBuffer, player.getName());
        }

        this.writeBufferPosition(byteBuffer, messageShift);
        writeS(byteBuffer, this.message);

        writeC(byteBuffer, 0);
    }

}
