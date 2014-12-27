package com.angelis.tera.game.presentation.network.packet.client;

import java.nio.ByteBuffer;

import com.angelis.tera.common.utils.PrintUtils;
import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraClientPacket;
import com.angelis.tera.game.process.model.enums.ObjectFamilyEnum;
import com.angelis.tera.game.process.services.PlayerService;

public class CM_GATHER_START extends TeraClientPacket {

    private ObjectFamilyEnum objectFamilyEnum;
    private int gatherUId;
    
    public CM_GATHER_START(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        this.gatherUId = readD();
        this.objectFamilyEnum = ObjectFamilyEnum.fromValue(PrintUtils.bytes2hex(readB(4)));
    }

    @Override
    protected void runImpl() {
        PlayerService.getInstance().onPlayerGatherStart(this.getConnection().getActivePlayer(), this.objectFamilyEnum, this.gatherUId);
    }

}
