package com.angelis.tera.game.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraServerPacket;

public class SM_SKILL_START_COOLTIME extends TeraServerPacket {

    private final int skillId;;
    private final int cooltime;

    public SM_SKILL_START_COOLTIME(final int skillId, final int cooltime) {
        this.skillId = skillId;
        this.cooltime = cooltime;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeD(byteBuffer, this.skillId);
        writeD(byteBuffer, this.cooltime);
    }
}
