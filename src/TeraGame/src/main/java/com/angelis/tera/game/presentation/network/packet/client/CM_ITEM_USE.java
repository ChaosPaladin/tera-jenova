package com.angelis.tera.game.presentation.network.packet.client;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraClientPacket;
import com.angelis.tera.game.process.model.visible.WorldPosition;
import com.angelis.tera.game.process.services.ItemService;

public class CM_ITEM_USE extends TeraClientPacket {

    private int itemId;
    private final WorldPosition worldPosition;
    
    public CM_ITEM_USE(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
        this.worldPosition = connection.getActivePlayer().getWorldPosition().clone();
    }

    @Override
    protected void readImpl() {
        readQ(); // playerUID
        this.itemId = readD();
        readB(28);
        this.worldPosition.setXYZ(readF(), readF(), readF());
        this.worldPosition.setHeading(readH());
        readB(12);
        readC(); // 0x01
    }

    @Override
    protected void runImpl() {
        ItemService.getInstance().onPlayerItemUse(this.getConnection().getActivePlayer(), this.itemId, this.worldPosition);
    }
}
