package com.angelis.tera.packet.process.network.packet.handlers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javafx.concurrent.Task;

import com.angelis.tera.packet.presentation.views.MainPacketView;
import com.angelis.tera.packet.process.network.packet.Packet;

public enum PacketHandlerEnum {

    CONSOLE(new AbstractPacketHandler() {
        @Override
        public void handle(final Packet packet) {
            System.out.println(packet.toDebugString());
        }
    }),

    FILE(new AbstractPacketHandler() {

        FileWriter fileWriter = null;

        @Override
        public void handle(final Packet packet) {
            try {
                if (fileWriter == null) {
                    fileWriter = new FileWriter(new File("export.log"));
                }
                fileWriter.write(packet.toDebugString());
            }
            catch (final IOException e) {
                throw new RuntimeException(e);
            }
        }
    }),

    UI(new AbstractPacketHandler() {
        @Override
        public void handle(final Packet packet) {
            new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    MainPacketView.instance.getController().addPacket(packet);
                    return null;
                }
                
            }.run();
        }
    });

    private final AbstractPacketHandler innerHandler;

    PacketHandlerEnum(final AbstractPacketHandler packetHandler) {
        this.innerHandler = packetHandler;
    }
    
    public final void handle(final Packet packet) {
        this.innerHandler.handle(packet);
    }
}
