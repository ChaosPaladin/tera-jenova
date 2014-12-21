package com.angelis.tera.game.network.packet.server;

import java.nio.ByteBuffer;
import java.util.List;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.pegasus.PegasusFly;

public class SM_PEGASUS_MAP_SHOW extends TeraServerPacket {

    private final List<PegasusFly> pegasusFlyPoints;

    public SM_PEGASUS_MAP_SHOW(final List<PegasusFly> pegasusFlyPoints) {
        this.pegasusFlyPoints = pegasusFlyPoints;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeH(byteBuffer, pegasusFlyPoints.size());
        writeH(byteBuffer, 8);
        
        int i = 0;
        for (final PegasusFly PegasusFlyPoint : this.pegasusFlyPoints) {
            
            writeH(byteBuffer, byteBuffer.position());
            
            final short nextPegasusShift = (short) byteBuffer.position();
            writeH(byteBuffer, 0);

            writeD(byteBuffer, PegasusFlyPoint.getId());
            writeD(byteBuffer, PegasusFlyPoint.getCost());
            writeD(byteBuffer, PegasusFlyPoint.getFromNameId());
            writeD(byteBuffer, PegasusFlyPoint.getToNameId());
            writeD(byteBuffer, PegasusFlyPoint.getLevel());

            if (i++ < this.pegasusFlyPoints.size()) {
                this.writeBufferPosition(byteBuffer, nextPegasusShift);
            }
        }
    }

}
