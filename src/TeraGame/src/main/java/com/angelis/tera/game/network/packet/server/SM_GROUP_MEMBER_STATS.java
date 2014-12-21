package com.angelis.tera.game.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.creature.BaseStats;
import com.angelis.tera.game.process.model.creature.CurrentStats;
import com.angelis.tera.game.process.model.player.Player;

public class SM_GROUP_MEMBER_STATS extends TeraServerPacket {

    private final Player player;
    
    public SM_GROUP_MEMBER_STATS(final Player player) {
        this.player = player;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        final CurrentStats currentStats = this.player.getCurrentStats();
        final BaseStats creatureBaseStats = this.player.getTemplate().getBaseStats();

        writeD(byteBuffer, 15);
        writeD(byteBuffer, this.player.getId());
        writeD(byteBuffer, currentStats.getHp());
        writeD(byteBuffer, currentStats.getMp());
        writeD(byteBuffer, creatureBaseStats.getBaseHp());
        writeD(byteBuffer, creatureBaseStats.getBaseMp());
        writeD(byteBuffer, this.player.getLevel());
        writeB(byteBuffer, "04000178000000000000000000000000000000");
    }

}
