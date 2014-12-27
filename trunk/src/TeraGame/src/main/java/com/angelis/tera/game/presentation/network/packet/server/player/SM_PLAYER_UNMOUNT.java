package com.angelis.tera.game.presentation.network.packet.server.player;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.mount.Mount;
import com.angelis.tera.game.process.model.player.Player;

public class SM_PLAYER_UNMOUNT extends TeraServerPacket {

    private final Player player;
    private final Mount mount;
    
    public SM_PLAYER_UNMOUNT(final Player player, final Mount mount) {
        this.player = player;
        this.mount = mount;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeUid(byteBuffer, this.player);
        writeD(byteBuffer, this.mount.getSkillId());
    }

}
