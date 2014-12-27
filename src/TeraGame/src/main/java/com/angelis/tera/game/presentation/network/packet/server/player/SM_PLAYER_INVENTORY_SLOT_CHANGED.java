package com.angelis.tera.game.presentation.network.packet.server.player;

import java.nio.ByteBuffer;
import java.util.List;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;

public class SM_PLAYER_INVENTORY_SLOT_CHANGED extends TeraServerPacket {

    private final List<Integer> slotChanged;

    public SM_PLAYER_INVENTORY_SLOT_CHANGED(final List<Integer> slotChanged) {
        this.slotChanged = slotChanged;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeH(byteBuffer, this.slotChanged.size());
        
        short thisShit = 0;
        writeH(byteBuffer, 8); 
        
        for (final Integer slotChange : this.slotChanged) {
            thisShit = (short) byteBuffer.position();
            writeH(byteBuffer, thisShit);// this shit
            writeH(byteBuffer, 0);// next shit
            writeC(byteBuffer, 0);
            writeD(byteBuffer, slotChange);
            
            this.writeBufferPosition(byteBuffer, thisShit+2);
        }
    }
}
