package com.angelis.tera.game.network.packet.client;

import java.nio.ByteBuffer;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraClientPacket;
import com.angelis.tera.game.process.model.enums.StorageTypeEnum;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.model.storage.Storage;
import com.angelis.tera.game.services.StorageService;

public class CM_ITEM_MOVE extends TeraClientPacket {
    
    private int fromSlot;
    private int toSlot;

    public CM_ITEM_MOVE(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        readQ(); // playerUid + playerFamilly
        this.fromSlot = readD();
        this.toSlot = readD();
    }

    @Override
    protected void runImpl() {
        final Player player = this.getConnection().getActivePlayer();
        final Storage storage = player.getStorage(StorageTypeEnum.INVENTORY);
        StorageService.getInstance().moveItem(player, storage, this.fromSlot, this.toSlot);
    }

}
