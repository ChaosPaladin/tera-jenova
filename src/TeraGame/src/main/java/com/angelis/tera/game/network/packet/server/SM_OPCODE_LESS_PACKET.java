package com.angelis.tera.game.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.common.utils.PrintUtils;
import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraServerPacket;

public class SM_OPCODE_LESS_PACKET extends TeraServerPacket {

    private final byte[] data;
    
    public SM_OPCODE_LESS_PACKET(final byte[] data) {
        this.data = data;
    }
    
    public SM_OPCODE_LESS_PACKET(final String data) {
        this(PrintUtils.hex2bytes(data.trim()));
    }
    
    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeB(byteBuffer, data);
    }
    
    @Override
    public void writeOpcode(final ByteBuffer byteBuffer) {}
}
