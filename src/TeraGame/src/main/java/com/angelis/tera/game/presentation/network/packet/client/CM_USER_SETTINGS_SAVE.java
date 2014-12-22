package com.angelis.tera.game.presentation.network.packet.client;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraClientPacket;

public class CM_USER_SETTINGS_SAVE extends TeraClientPacket {

    private byte[] settings;
    
    public CM_USER_SETTINGS_SAVE(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        this.settings = readAll();
    }

    @Override
    protected void runImpl() {
        // TODO
        this.getConnection().getActivePlayer().setUserSettings(settings);
    }
}
