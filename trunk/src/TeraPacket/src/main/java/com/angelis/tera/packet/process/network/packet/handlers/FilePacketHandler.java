package com.angelis.tera.packet.process.network.packet.handlers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.angelis.tera.packet.process.network.packet.Packet;

public class FilePacketHandler extends AbstractPacketHandler {

    private FileWriter fileWriter = null;
    
    public FilePacketHandler() {
        try {
            fileWriter = new FileWriter(new File("export.log"));
        }
        catch (final IOException e) {
            log.error(e.getMessage(), e);
        }
    }
    
    @Override
    public void handle(final Packet packet) {
        try {
            fileWriter.write(packet.toString());
        }
        catch (final IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
