package com.angelis.tera.game.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.player.Player;

public class SM_WHISP extends TeraServerPacket {

    private final Player sender;
    private final Player receiver;
    private final String whisper;
    
    public SM_WHISP(final Player sender, final Player receiver, final String whisper) {
        this.sender = sender;
        this.receiver = receiver;
        this.whisper = whisper;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeH(byteBuffer, 0); //first name shift
        writeH(byteBuffer, 0); //second name shift
        writeH(byteBuffer, 0); //message shift
        
        writeC(byteBuffer, 0);

        this.writeBufferPosition(byteBuffer, 4);
        writeS(byteBuffer, this.sender.getName());

        this.writeBufferPosition(byteBuffer, 6);
        writeS(byteBuffer, this.receiver.getName());

        this.writeBufferPosition(byteBuffer, 8);
        writeS(byteBuffer, this.whisper);
    }
}
