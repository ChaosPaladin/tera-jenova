package com.angelis.tera.game.presentation.network.packet.client;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraClientPacket;
import com.angelis.tera.game.presentation.network.packet.server.SM_QUIT_GAME_CANCEL;
import com.angelis.tera.game.process.services.ThreadPoolService;
import com.angelis.tera.game.process.tasks.TaskTypeEnum;

public class CM_QUIT_GAME_CANCEL extends TeraClientPacket {

    public CM_QUIT_GAME_CANCEL(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        // Empty packet
    }

    @Override
    protected void runImpl() {
        this.getConnection().sendPacket(new SM_QUIT_GAME_CANCEL());
        ThreadPoolService.getInstance().cancelTask(this.getConnection().getActivePlayer(), TaskTypeEnum.PLAYER_QUIT);
    }
}
