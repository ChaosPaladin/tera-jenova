package com.angelis.tera.game.network.packet.client;

import java.nio.ByteBuffer;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraClientPacket;
import com.angelis.tera.game.process.model.player.enums.ReportTypeEnum;

public class CM_PLAYER_REPORT extends TeraClientPacket {

    private ReportTypeEnum reportType;
    private String playerName;
    
    public CM_PLAYER_REPORT(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        readH(); // unk (0A00)
        this.reportType = ReportTypeEnum.fromValue(readD());
        this.playerName = readS();
    }

    @Override
    protected void runImpl() {
        // TODO
    }

}
