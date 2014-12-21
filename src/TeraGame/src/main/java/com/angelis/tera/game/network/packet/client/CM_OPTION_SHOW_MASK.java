package com.angelis.tera.game.network.packet.client;

import java.nio.ByteBuffer;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraClientPacket;
import com.angelis.tera.game.network.packet.server.SM_OPTION_SHOW_MASK;

public class CM_OPTION_SHOW_MASK extends TeraClientPacket {

    private boolean maskVisible;

    public CM_OPTION_SHOW_MASK(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        this.maskVisible = (readC() == 1);
    }

    @Override
    protected void runImpl() {
        this.getConnection().sendPacket(new SM_OPTION_SHOW_MASK(this.maskVisible));
    }

}
