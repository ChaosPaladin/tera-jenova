package com.angelis.tera.game.network.packet.server;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraServerPacket;

public class SM_SYSTEM_MESSAGE extends TeraServerPacket {

    private final String[] args;
    
    public SM_SYSTEM_MESSAGE(final String[] args) {
        this.args = args;
    }
    
    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        for (int i = 0 ; i < args.length ; i++) {
            try {
                writeH(byteBuffer, i == 0 ? 6 : 11);
                final String[] s = args[i].split("");
                for (int j = 0 ; j < s.length ; j++) {
                    writeB(byteBuffer, s[j].getBytes("UTF-8"));
                    writeC(byteBuffer, 0);
                }
            }
            catch (final UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        writeH(byteBuffer, 0);
    }
}
