package com.angelis.tera.game.presentation.network.packet.client.group;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraClientPacket;
import com.angelis.tera.game.process.model.enums.ObjectFamilyEnum;
import com.angelis.tera.game.process.services.GroupService;
import com.angelis.tera.game.process.services.ObjectIDService;

public class CM_GROUP_CONFIRM_LEADER_CHANGE extends TeraClientPacket {

    private int playerUID;

    public CM_GROUP_CONFIRM_LEADER_CHANGE(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        readD(); // Group common packet
        this.playerUID = readD();
    }

    @Override
    protected void runImpl() {
        GroupService.getInstance().onPlayerConfirmLeaderChange(this.getConnection().getActivePlayer(), ObjectIDService.getInstance().getObjectByUId(ObjectFamilyEnum.PLAYER, this.playerUID));
    }
}
