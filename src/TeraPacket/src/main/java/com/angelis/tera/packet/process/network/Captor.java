package com.angelis.tera.packet.process.network;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.apache.log4j.Logger;
import org.jnetpcap.packet.Payload;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.PcapPacketHandler;
import org.jnetpcap.protocol.network.Ip4;

import com.angelis.tera.packet.process.network.packet.Packet;
import com.angelis.tera.packet.process.network.packet.handlers.PacketHandlerEnum;
import com.angelis.tera.packet.process.network.packet.protocols.IProtocol;
import com.google.inject.Inject;

public class Captor implements PcapPacketHandler<String> {

    /** LOGGER */
    private static Logger log = Logger.getLogger(Captor.class.getName());

    // This is used internally
    private final Ip4 ip = new Ip4();
    private final Payload payload = new Payload();

    private final IProtocol protocol;

    @Inject
    public Captor(final IProtocol protocol) {
        this.protocol = protocol;
    }

    @Override
    public void nextPacket(final PcapPacket pcapPacket, final String dummy) {
        if (!pcapPacket.hasHeader(ip)) {
            log.info("Ignored packet");
            return;
        }
        this.protocol.nextPacket(pcapPacket, ip);

        if (!pcapPacket.hasHeader(payload)) {
            return;
        }

        final ByteBuffer byteBuffer = ByteBuffer.wrap(payload.data());
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        final Packet packet = this.protocol.handlePacket(byteBuffer, ip);
        
        if (packet != null) {
            // Handler packet
            for (final PacketHandlerEnum packetHandler : this.protocol.getPacketHandlers()) {
                packetHandler.handle(packet);
            }
        }
    }
}
