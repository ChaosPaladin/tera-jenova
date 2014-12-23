package com.angelis.tera.game.presentation.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.WelcomeMessage;

public class SM_WELCOME_MESSAGE extends TeraServerPacket {

    private final WelcomeMessage welcomeMessage;

    public SM_WELCOME_MESSAGE(final WelcomeMessage welcomeMessage) {
        this.welcomeMessage = welcomeMessage;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        final int titleShift = byteBuffer.position();
        writeH(byteBuffer, 0);
        
        final int contentShift = byteBuffer.position();
        writeH(byteBuffer, 0);
        
        writeB(byteBuffer, "53000000");
        
        writeBufferPosition(byteBuffer, titleShift);
        writeS(byteBuffer, this.welcomeMessage.getTitle());
        
        writeBufferPosition(byteBuffer, contentShift);
        writeS(byteBuffer, this.welcomeMessage.getContent());
    }
}
