package com.angelis.tera.game.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.model.player.enums.PlayerMoveTypeEnum;

public class SM_PLAYER_MOVE extends TeraServerPacket {

    private final Player player;
    
    private final float x1;
    private final float x2;
    private final float z1;
    
    private final float y1;
    private final float y2;
    private final float z2;
    
    private final short heading;
    private final PlayerMoveTypeEnum playerMoveType;
    
    protected final byte[] unk2;
    protected final int unk3;
    
    public SM_PLAYER_MOVE(final Player player, final float x1, final float y1, final float z1, final short heading, final float x2, final float y2, final float z2, final PlayerMoveTypeEnum playerMoveType, final byte[] unk2, final int unk3) {
        this.player = player;
        this.x1 = x1;
        this.y1 = y1;
        this.z1 = z1;
        this.heading = heading;
        this.x2 = x2;
        this.y2 = y2;
        this.z2 = z2;
        
        this.playerMoveType = playerMoveType;
        this.unk2 = unk2;
        this.unk3 = unk3;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeUid(byteBuffer, this.player);

        writeF(byteBuffer, x1);
        writeF(byteBuffer, y1);
        writeF(byteBuffer, z1);
        writeH(byteBuffer, heading);

        writeB(byteBuffer, "AA00"); // TODO player movement

        writeF(byteBuffer, x2);
        writeF(byteBuffer, y2);
        writeF(byteBuffer, z2);

        writeH(byteBuffer, playerMoveType.value);
        writeH(byteBuffer, 0);
        writeC(byteBuffer, 0);
    }
}
