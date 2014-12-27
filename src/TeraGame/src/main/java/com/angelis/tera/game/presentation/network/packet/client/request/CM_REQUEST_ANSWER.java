package com.angelis.tera.game.presentation.network.packet.client.request;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraClientPacket;
import com.angelis.tera.game.process.services.RequestService;

public class CM_REQUEST_ANSWER extends TeraClientPacket {

    private int requestUid;
    private String initiatorName;
    private boolean accept;

    public CM_REQUEST_ANSWER(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        readH(); // initiatorName shift
        readD(); // request type
        this.requestUid = readD();
        readD(); // request Id ??
        accept = readD() == 1; // 2 if player refuse
        readB(new byte[8]); // unk C527000001000000
        this.initiatorName = readS();
    }

    @Override
    protected void runImpl() {
        RequestService.getInstance().onPlayerResponse(this.getConnection().getActivePlayer(), requestUid, accept);
    }
}
