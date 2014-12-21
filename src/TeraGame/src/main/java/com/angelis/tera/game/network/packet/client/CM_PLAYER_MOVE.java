package com.angelis.tera.game.network.packet.client;

import java.nio.ByteBuffer;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraClientPacket;
import com.angelis.tera.game.process.model.player.enums.PlayerMoveTypeEnum;
import com.angelis.tera.game.services.PlayerService;

public class CM_PLAYER_MOVE extends TeraClientPacket {

    private float x1;
    private float x2;
    
    private float y1;
    private float y2;
    
    private float z1;
    private float z2;
    
    private short heading;
    private PlayerMoveTypeEnum moveType;
    
    protected byte[] unk2;
    protected int unk3;
    
    public CM_PLAYER_MOVE(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        // E6307347E318A6C7497A5EC529CAE6307347E318A6C781AB5EC5050000000000001ED24000
        this.x1 = readF();
        this.y1 = readF();
        this.z1 = readF();
        this.heading = readH();

        this.x2 = readF();
        this.y2 = readF();
        this.z2 = readF();

        this.moveType = PlayerMoveTypeEnum.fromValue(readH());
        this.unk2 = readB(5);
        this.unk3 = readD();        
    }

    @Override
    protected void runImpl() {
        PlayerService.getInstance().onPlayerMove(this.getConnection().getActivePlayer(), x1, y1, z1, heading, x2, y2, z2, moveType, unk2, unk3);
    }
}
