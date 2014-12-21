package com.angelis.tera.game.network.packet.client;

import java.nio.ByteBuffer;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraClientPacket;
import com.angelis.tera.game.services.RequestService;

public class CM_REQUEST_ACCEPT extends TeraClientPacket {

    private int requestUid;
    private String initiatorName;

    public CM_REQUEST_ACCEPT(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        readB(new byte[6]); // unk 160004000000
        this.requestUid = readD();
        readB(new byte[8]); // unk C527000001000000
        this.initiatorName = readS();
    }

    @Override
    protected void runImpl() {
        RequestService.getInstance().onPlayerResponse(this.getConnection().getActivePlayer(), requestUid);
    }

}
