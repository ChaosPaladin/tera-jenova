package com.angelis.tera.game.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.player.Player;

public class SM_CHAT_INFO extends TeraServerPacket {

    private final Player player;
    private final int version;
    private final int type;
    
    public SM_CHAT_INFO(final Player player, final int version, final int type) {
        this.player = player;
        this.version = version;
        this.type = type;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        final int nameStartShift = byteBuffer.position();
        writeH(byteBuffer, 0);
        
        writeD(byteBuffer, this.type);
        writeD(byteBuffer, this.player.getRaceGenderClassValue());
        writeD(byteBuffer, this.player.getLevel());

        writeH(byteBuffer, 0); // if 4 we have a "leave the guild" link
        writeD(byteBuffer, this.version);

        this.writeBufferPosition(byteBuffer, nameStartShift);
        writeS(byteBuffer, this.player.getName());
    }
}
