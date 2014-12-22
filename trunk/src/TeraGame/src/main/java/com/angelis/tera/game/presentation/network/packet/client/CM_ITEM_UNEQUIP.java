package com.angelis.tera.game.presentation.network.packet.client;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraClientPacket;
import com.angelis.tera.game.process.model.enums.StorageTypeEnum;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.model.storage.Storage;
import com.angelis.tera.game.process.services.StorageService;

public class CM_ITEM_UNEQUIP extends TeraClientPacket {

    private int fromSlot;
    private int toSlot;
    private int itemId;
    
    public CM_ITEM_UNEQUIP(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        readQ(); // playerUid + playerFamilly
        this.fromSlot = readD();
        this.toSlot = readD();
        this.itemId = readD();
    }

    @Override
    protected void runImpl() {
        final Player player = this.getConnection().getActivePlayer();
        final Storage storage = player.getStorage(StorageTypeEnum.INVENTORY);
        StorageService.getInstance().moveItem(player, storage, this.itemId, fromSlot, toSlot);
    }
}
