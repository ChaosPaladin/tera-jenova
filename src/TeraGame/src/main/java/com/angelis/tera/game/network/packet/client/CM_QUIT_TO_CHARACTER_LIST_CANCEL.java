package com.angelis.tera.game.network.packet.client;

import java.nio.ByteBuffer;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraClientPacket;
import com.angelis.tera.game.network.packet.server.SM_QUIT_TO_CHARACTER_LIST_CANCEL;
import com.angelis.tera.game.services.ThreadPoolService;
import com.angelis.tera.game.tasks.TaskTypeEnum;

public class CM_QUIT_TO_CHARACTER_LIST_CANCEL extends TeraClientPacket {

    public CM_QUIT_TO_CHARACTER_LIST_CANCEL(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        // Empty packet
    }

    @Override
    protected void runImpl() {
        this.getConnection().sendPacket(new SM_QUIT_TO_CHARACTER_LIST_CANCEL());
        ThreadPoolService.getInstance().cancelTask(this.getConnection().getActivePlayer(), TaskTypeEnum.PLAYER_QUIT_TO_PLAYER_LIST);
    }

}
