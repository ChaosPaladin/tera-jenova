package com.angelis.tera.game.presentation.network.packet.client;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraClientPacket;
import com.angelis.tera.game.process.services.DialogService;

public class CM_DIALOG_EVENT extends TeraClientPacket {

    private int dialogUid;
    private int page;
    
    public CM_DIALOG_EVENT(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        this.dialogUid = readD(); // dialog id
        this.page = readD();
        readD(); // unk 0x00
    }

    @Override
    protected void runImpl() {
        DialogService.getInstance().onDialogEvent(this.getConnection().getActivePlayer(), this.page, this.dialogUid);
    }
}
