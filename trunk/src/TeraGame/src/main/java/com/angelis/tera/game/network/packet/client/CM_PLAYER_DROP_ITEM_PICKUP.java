package com.angelis.tera.game.network.packet.client;

import java.nio.ByteBuffer;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraClientPacket;
import com.angelis.tera.game.services.DropService;

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
