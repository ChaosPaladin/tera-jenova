package com.angelis.tera.game.presentation.network.packet.server.dialog;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;

public class SM_DIALOG_MENU_SELECT extends TeraServerPacket {

    private final int menu;
    
    public SM_DIALOG_MENU_SELECT(final int menu) {
        this.menu = menu;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeD(byteBuffer, this.menu);
    }

}
