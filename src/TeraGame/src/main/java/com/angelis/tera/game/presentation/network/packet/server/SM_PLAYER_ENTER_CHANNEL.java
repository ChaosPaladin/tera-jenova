package com.angelis.tera.game.presentation.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;

public class SM_PLAYER_ENTER_CHANNEL extends TeraServerPacket {

    private final int canalNumber;
    
    public SM_PLAYER_ENTER_CHANNEL(final int canalNumber) {
        super();
        this.canalNumber = canalNumber;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeH(byteBuffer, 0);
        writeC(byteBuffer, 0);
        writeD(byteBuffer, canalNumber);
        writeD(byteBuffer, 1);
    }
}
