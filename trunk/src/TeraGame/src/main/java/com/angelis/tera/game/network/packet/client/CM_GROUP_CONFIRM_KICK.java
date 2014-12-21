
package com.angelis.tera.game.network.packet.client;

import java.nio.ByteBuffer;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraClientPacket;
import com.angelis.tera.game.process.model.group.enums.GroupVoteRequestEnum;
import com.angelis.tera.game.services.GroupService;

public class CM_GROUP_CONFIRM_KICK extends TeraClientPacket {

    private int playerId;

    public CM_GROUP_CONFIRM_KICK(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        readD(); // unk 0x0E000000
        this.playerId = readD();
        readC(); // 0x01
    }

    @Override
    protected void runImpl() {
        GroupService.getInstance().onPlayerConfirmRequestVote(this.getConnection().getActivePlayer(), GroupVoteRequestEnum.KICK, this.playerId);
    }

}
