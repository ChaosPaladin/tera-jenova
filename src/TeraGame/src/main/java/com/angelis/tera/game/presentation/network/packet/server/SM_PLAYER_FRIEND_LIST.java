package com.angelis.tera.game.presentation.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.model.player.PlayerRelation;

public class SM_PLAYER_FRIEND_LIST extends TeraServerPacket {

    private final Player player;

    public SM_PLAYER_FRIEND_LIST(final Player player) {
        this.player = player;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        final int friendSize = this.player.getRelations().size();
        writeH(byteBuffer, (short) friendSize);

        if (friendSize > 0) {
            writeH(byteBuffer, 8);
            int i = 0;
            for(final PlayerRelation playerRelation : this.player.getRelations()) {
                final Player friend = playerRelation.getTarget();
                
                writeH(byteBuffer, (short) byteBuffer.position());
                
                final int shift = byteBuffer.position();
                writeH(byteBuffer, 0); // end friend shift
                writeH(byteBuffer, 0); // start name shift
                writeH(byteBuffer, 0); // end name shift
                writeD(byteBuffer, friend.getId());
                writeD(byteBuffer, friend.getLevel());
                writeD(byteBuffer, friend.getRace().value);
                writeD(byteBuffer, friend.getPlayerClass().value);
                writeB(byteBuffer, friend.getCurrentZoneData());
                writeB(byteBuffer, playerRelation.getNote());

                this.writeBufferPosition(byteBuffer, shift + 2);
                writeS(byteBuffer, friend.getName());

                this.writeBufferPosition(byteBuffer, shift + 4);
                writeH(byteBuffer, 0);

                if (friendSize - 1 > i++) {
                    this.writeBufferPosition(byteBuffer, shift);
                }
            }
        }
    }
}
