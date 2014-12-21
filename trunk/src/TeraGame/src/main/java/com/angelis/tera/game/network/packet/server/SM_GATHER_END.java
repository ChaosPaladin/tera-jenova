package com.angelis.tera.game.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.gather.Gather;
import com.angelis.tera.game.process.model.gather.enums.GatherResultEnum;
import com.angelis.tera.game.process.model.player.Player;

public class SM_GATHER_END extends TeraServerPacket {

    private final Player player;
    private final Gather gather;
    private final GatherResultEnum gatherResult;
    
    public SM_GATHER_END(final Player player, final Gather gather, final GatherResultEnum gatherResult) {
        this.player = player;
        this.gather = gather;
        this.gatherResult = gatherResult;
    }
    
    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeUid(byteBuffer, this.player);
        writeUid(byteBuffer, this.gather);
        writeD(byteBuffer, this.gatherResult.value);
        writeD(byteBuffer, this.player.getGatherCraftPoints());
    }
}
