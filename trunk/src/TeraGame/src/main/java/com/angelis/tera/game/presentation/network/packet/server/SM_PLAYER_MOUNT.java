package com.angelis.tera.game.presentation.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.mount.Mount;
import com.angelis.tera.game.process.model.player.Player;

public class SM_PLAYER_MOUNT extends TeraServerPacket {

    private final Player player;
    private final Mount mount;

    public SM_PLAYER_MOUNT(final Player player, final Mount mount) {
        this.player = player;
        this.mount = mount;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeUid(byteBuffer, this.player);
        writeD(byteBuffer, this.mount.getId());
        writeD(byteBuffer, this.mount.getSkillId());
        writeC(byteBuffer, 0);
    }
}
