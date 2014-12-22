package com.angelis.tera.game.presentation.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.player.gather.GatherStats;
import com.angelis.tera.game.process.model.player.gather.enums.GatherTypeEnum;

public class SM_PLAYER_GATHER_STATS extends TeraServerPacket {

    private final GatherStats gatherStats;
    
    public SM_PLAYER_GATHER_STATS(final GatherStats gatherStats) {
        this.gatherStats = gatherStats;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeH(byteBuffer, this.gatherStats.getGatherLevel(GatherTypeEnum.ESSENCE));
        writeH(byteBuffer, this.gatherStats.getGatherLevel(GatherTypeEnum.PLANT));
        writeH(byteBuffer, 0); //unk, mb bughunting
        writeH(byteBuffer, this.gatherStats.getGatherLevel(GatherTypeEnum.MINE));
        
        // TODO
        writeH(byteBuffer, 0); // energy bonus
        writeH(byteBuffer, 0); // herb bonus
        writeH(byteBuffer, 0); // bughunting bonus
        writeH(byteBuffer, 0); // mine bonus
    }
}
