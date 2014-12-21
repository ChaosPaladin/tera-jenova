package com.angelis.tera.game.network.packet.client;

import java.nio.ByteBuffer;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraClientPacket;
import com.angelis.tera.game.services.ChatService;

public class CM_WHISP extends TeraClientPacket {

    private String name;
    private String whisper;
    
    public CM_WHISP(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        readH(); // 08 00
        readH(); // 16 00
        this.name = readS();
        this.whisper = readS();
    }

    @Override
    protected void runImpl() {
        ChatService.getInstance().onPlayerWhisper(this.getConnection().getActivePlayer(), this.name, this.whisper);
    }
}
