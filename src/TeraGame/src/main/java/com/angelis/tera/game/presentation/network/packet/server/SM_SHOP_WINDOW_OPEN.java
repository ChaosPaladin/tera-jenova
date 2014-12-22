package com.angelis.tera.game.presentation.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.config.NetworkConfig;
import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;

public class SM_SHOP_WINDOW_OPEN extends TeraServerPacket {

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeS(byteBuffer, NetworkConfig.SHOP_URL);
    }
}
