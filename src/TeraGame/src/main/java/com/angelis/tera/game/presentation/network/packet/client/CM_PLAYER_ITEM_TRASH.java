package com.angelis.tera.game.presentation.network.packet.client;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraClientPacket;
import com.angelis.tera.game.process.model.enums.StorageTypeEnum;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.model.storage.Storage;
import com.angelis.tera.game.process.services.StorageService;

public class CM_PLAYER_ITEM_TRASH extends TeraClientPacket {

    private int fromSlot;
    private int itemAmount;

    public CM_PLAYER_ITEM_TRASH(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        readQ(); // playerUid + playerFamilly
        this.fromSlot = readD() + 20;
        this.itemAmount = readD();
    }
    
    @Override
    protected void runImpl() {
        final Player player = this.getConnection().getActivePlayer();
        final Storage storage = player.getStorage(StorageTypeEnum.INVENTORY);
        StorageService.getInstance().removeItemBySlot(player, storage, this.fromSlot, this.itemAmount);
    }
}
