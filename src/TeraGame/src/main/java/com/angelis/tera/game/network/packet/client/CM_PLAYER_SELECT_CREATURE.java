package com.angelis.tera.game.network.packet.client;

import java.nio.ByteBuffer;

import com.angelis.tera.common.utils.PrintUtils;
import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraClientPacket;
import com.angelis.tera.game.process.model.enums.ObjectFamilyEnum;
import com.angelis.tera.game.services.PlayerService;

public class CM_PLAYER_SELECT_CREATURE extends TeraClientPacket {

    public Integer creatureUid;
    public ObjectFamilyEnum objectFamilly;
    
    public CM_PLAYER_SELECT_CREATURE(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        this.creatureUid = readD();
        this.objectFamilly = ObjectFamilyEnum.fromValue(PrintUtils.bytes2hex(readB(4)));
    }

    @Override
    protected void runImpl() {
        PlayerService.getInstance().onPlayerSelectCreature(this.getConnection().getActivePlayer(), this.creatureUid, this.objectFamilly);
    }
}
