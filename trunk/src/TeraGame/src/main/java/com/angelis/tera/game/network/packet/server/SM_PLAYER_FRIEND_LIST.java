package com.angelis.tera.game.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.model.player.PlayerRelation;

public class SM_PLAYER_FRIEND_LIST extends TeraServerPacket {

    private final Player player;

    public SM_PLAYER_FRIEND_LIST(final Player player) {
        this.player = player;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        // ADF50D0008000800430031004100BE4203002C000000010000000600000001000000010000000C0000007A0000000074007900740061006E00670065000000000043007E006C007C00AB4C03002500000004000000040000000100000001000000040000001A000000004400E9006D006F006E0079006100000000007E00B700A700B500294D03001000000000000000000000000100000001000000030000000E000000004500700069006F006E00E90000000000B700F600E000F400637303002700000003000000020000000100000001000000040000001A0000000044007900610062006C00650073007300650000000000F60033011F0131011B6104002100000000000000070000000100000001000000050000002F0000000041006E00670065006C007900630061000000000033016C015C016A0198040500120000000100000001000000010000000100000003000000100000000041006E007500640069006100000000006C01A5019501A30108660500250000000100000001000000010000000100000005000000290000000054006800650069007200610000000000A501DE01CE01DC01698305001D00000000000000030000000100000001000000050000002A0000000046006500720069006E00610000000000DE011D0207021B02531306002600000000000000000000000100000001000000050000002900000000630068007200690073006900720061007800000000001D025C0246025A02213E07003C00000001000000040000000100000001000000050000002A000000004D00E90072007900740061006D006F006E00000000005C029D0285029B02790508003C0000000100000006000000010000000100000001000000010000000042007200610065007A0065006E00740069006100000000009D02D802C602D602B2CF09000A000000000000000400000001000000010000000100000001000000005200650067006C0079007300730000000000D802000001030B03A5090B001100000001000000040000000100000001000000030000000E000000004E0069006900730000000000
        
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

                if (friendSize - 1 > i) {
                    this.writeBufferPosition(byteBuffer, shift);
                }
                i++;
            }
        }
    }
}
