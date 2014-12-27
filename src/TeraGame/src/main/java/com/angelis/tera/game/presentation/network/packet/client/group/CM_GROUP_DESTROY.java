package com.angelis.tera.game.presentation.network.packet.client.group;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraClientPacket;
import com.angelis.tera.game.process.services.GroupService;

public class CM_GROUP_DESTROY extends TeraClientPacket {

    public CM_GROUP_DESTROY(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        // Empty packet
    }

    @Override
    protected void runImpl() {
        GroupService.getInstance().onGroupRequestDestroy(this.getConnection().getActivePlayer());
    }

}
