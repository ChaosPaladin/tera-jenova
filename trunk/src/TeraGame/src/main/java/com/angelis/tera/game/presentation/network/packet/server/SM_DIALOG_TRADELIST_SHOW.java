package com.angelis.tera.game.presentation.network.packet.server;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map.Entry;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.creature.Npc;
import com.angelis.tera.game.process.model.dialog.enums.DialogStringEnum;
import com.angelis.tera.game.process.model.player.request.AbstractRequest;
import com.angelis.tera.game.process.model.tradelist.Tradelist;
import com.angelis.tera.game.process.model.tradelist.enums.TradelistTabNameEnum;

public class SM_DIALOG_TRADELIST_SHOW extends TeraServerPacket {

    private final Npc npc;
    private final AbstractRequest<?, ?> request;
    private final Tradelist tradelist;
    private final DialogStringEnum dialogString;
    
    public SM_DIALOG_TRADELIST_SHOW(final Npc npc, final AbstractRequest<?, ?> request, final Tradelist tradelist, final DialogStringEnum dialogString) {
        this.npc = npc;
        this.request = request;
        this.tradelist = tradelist;
        this.dialogString = dialogString;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeH(byteBuffer, this.tradelist.getItemTabs().size());
        
        final int firstItemShift = byteBuffer.position();
        writeH(byteBuffer, 0);

        writeUid(byteBuffer, this.npc);
        writeD(byteBuffer, this.request.getUid());
        writeD(byteBuffer, dialogString.value);
        writeB(byteBuffer, "00000020");
        writeB(byteBuffer, "85EBB13F");
        
        this.writeBufferPosition(byteBuffer, firstItemShift);
        
        int i = 0;
        for (final Entry<TradelistTabNameEnum, List<Integer>> entry : this.tradelist.getItemTabs().entrySet()) {
            
            writeH(byteBuffer, byteBuffer.position());
            
            final int nextTabItemShift = byteBuffer.position();
            writeH(byteBuffer, 0);
            
            writeH(byteBuffer, entry.getValue().size());
            
            writeH(byteBuffer, byteBuffer.position()+6);
            writeD(byteBuffer, entry.getKey().value);
            
            int j = 0;
            for (final Integer integer : entry.getValue()) {
                writeH(byteBuffer, byteBuffer.position());
                
                final int nextItemShift = byteBuffer.position();
                writeH(byteBuffer, 0);
                writeD(byteBuffer, integer);
                
                if (++j < entry.getValue().size()) {
                    this.writeBufferPosition(byteBuffer, nextItemShift);
                }
            }
            
            if (++i < this.tradelist.getItemTabs().size()) {
                this.writeBufferPosition(byteBuffer, nextTabItemShift);
            }
        }
    }

}
