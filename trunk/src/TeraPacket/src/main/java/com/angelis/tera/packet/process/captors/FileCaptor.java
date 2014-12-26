package com.angelis.tera.packet.process.captors;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;

import com.angelis.tera.common.utils.PrintUtils;
import com.angelis.tera.packet.process.enums.PacketDirectionEnum;

public class FileCaptor extends AbstractCaptor {

    private FileWriter fileWriter = null;
    
    public FileCaptor() {
        try {
            fileWriter = new FileWriter(new File("export.log"));
        }
        catch (final IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void handlePacket(final ByteBuffer byteBuffer, final PacketDirectionEnum packetDirection) {
        final StringBuilder sb = new StringBuilder();
        switch (packetDirection) {
            case CLIENT_SERVER:
                sb.append("C -> S");
            break;
            case SERVER_CLIENT:
                sb.append("S -> C");
            break;
        }
        sb.append(System.getProperty("line.separator"));
        sb.append(PrintUtils.toHex(byteBuffer));
        sb.append(System.getProperty("line.separator"));
        sb.append(System.getProperty("line.separator"));

        try {
            fileWriter.write(sb.toString());
            System.out.println(sb.toString());
        }
        catch (final IOException e) {
            e.printStackTrace();
        }
    }

}
