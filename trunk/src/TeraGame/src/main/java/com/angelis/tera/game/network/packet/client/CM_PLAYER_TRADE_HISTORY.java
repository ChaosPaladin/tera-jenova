package com.angelis.tera.game.network.packet.client;

import java.nio.ByteBuffer;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraClientPacket;
import com.angelis.tera.game.process.model.enums.ObjectFamilyEnum;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.services.ObjectIDService;

public class CM_PLAYER_TRADE_HISTORY extends TeraClientPacket {

    private int playerUid;

    public CM_PLAYER_TRADE_HISTORY(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        this.playerUid = readD();
    }

    @Override
    protected void runImpl() {
        final Player player = ObjectIDService.getInstance().getObjectByUId(ObjectFamilyEnum.PLAYER, this.playerUid);
        if (player == null) {
            return;
        }
        
        
    }

}
