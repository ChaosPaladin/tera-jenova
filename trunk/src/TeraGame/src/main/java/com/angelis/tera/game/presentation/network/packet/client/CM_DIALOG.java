package com.angelis.tera.game.presentation.network.packet.client;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraClientPacket;
import com.angelis.tera.game.process.services.DialogService;

public class CM_DIALOG extends TeraClientPacket {

    protected int dialogUid;
    protected int selectedIndex;
    
    public CM_DIALOG(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        this.dialogUid = readD();
        this.selectedIndex = readD();
        readD(); //FFFFFFFF
        readD(); //FFFFFFFF
    }

    @Override
    protected void runImpl() {
        DialogService.getInstance().onDialog(this.getConnection().getActivePlayer(), this.dialogUid, this.selectedIndex);
    }
}
