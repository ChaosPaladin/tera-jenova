package com.angelis.tera.game.presentation.network.packet.client;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraClientPacket;
import com.angelis.tera.game.process.services.DropService;

public class CM_PLAYER_DROP_ITEM_PICKUP extends TeraClientPacket {

    private int dropItemUid;

    public CM_PLAYER_DROP_ITEM_PICKUP(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        this.dropItemUid = readD();
        readD(); // familly
    }

    @Override
    protected void runImpl() {
        DropService.getInstance().pickupItem(this.getConnection().getActivePlayer(), this.dropItemUid);
    }
}
