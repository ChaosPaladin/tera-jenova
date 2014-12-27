package com.angelis.tera.game.presentation.network.packet.client;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraClientPacket;
import com.angelis.tera.game.process.model.enums.StorageTypeEnum;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.model.storage.Storage;
import com.angelis.tera.game.process.services.StorageService;

public class CM_PLAYER_DUNGEON_COOLTIME_LIST extends TeraClientPacket {

    public CM_PLAYER_DUNGEON_COOLTIME_LIST(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        // Empty packet
    }

    @Override
    protected void runImpl() {
        final Player player = this.getConnection().getActivePlayer();
        final Storage storage = player.getStorage(StorageTypeEnum.INVENTORY);
        StorageService.getInstance().showStorage(player, storage, false);
    }
}
