package com.angelis.tera.packet.process.network.packet.handlers;

import com.angelis.tera.packet.process.network.packet.Packet;

public class ConsolePacketHandler extends AbstractPacketHandler {
    @Override
    public void handle(final Packet packet) {
        System.out.println(packet.toString());
    }
}
