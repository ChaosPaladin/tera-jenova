package com.angelis.tera.packet.process.network;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;
import java.util.Map;

import javolution.util.FastList;
import javolution.util.FastMap;

import org.apache.log4j.Logger;
import org.jnetpcap.packet.Payload;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.PcapPacketHandler;
import org.jnetpcap.protocol.network.Ip4;

import com.angelis.tera.common.network.crypt.CryptSession;
import com.angelis.tera.common.network.crypt.CryptState;
import com.angelis.tera.common.network.packet.AbstractPacket;
import com.angelis.tera.packet.process.Session;
import com.angelis.tera.packet.process.network.packet.Packet;
import com.angelis.tera.packet.process.network.packet.enums.PacketDirectionEnum;
import com.angelis.tera.packet.process.network.packet.handlers.AbstractPacketHandler;

public class Captor implements PcapPacketHandler<String> {

    /** LOGGER */
    private static Logger log = Logger.getLogger(Captor.class.getName());

    protected Session session;
    private final List<AbstractPacketHandler> packetHandlers;
    private final Map<Short, Packet> receivedPackets = new FastMap<>();

    private final List<Packet> creatureSpawns = new FastList<>();
    private final List<Packet> gatherSpawns = new FastList<>();

    // This is used internally
    private final Ip4 ip = new Ip4();
    private final Payload payload = new Payload();

    public Captor(final List<AbstractPacketHandler> packetHandlers) {
        this.packetHandlers = packetHandlers;
    }
    
    @Override
    public void nextPacket(final PcapPacket packet, final String dummy) {
        if (!packet.hasHeader(ip)) {
            log.info("Ignored packet");
            return;
        }

        if (this.session == null) {
            this.session = new Session();
            try {
                // First packet is inited by client
                this.session.setLocalAddress(InetAddress.getByAddress(ip.source()));
            }
            catch (final UnknownHostException e) {
                log.error(e.getMessage(), e);
            }
        }

        if (!packet.hasHeader(payload)) {
            return;
        }

        final ByteBuffer byteBuffer = ByteBuffer.wrap(payload.getByteArray(0, payload.size()));
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        this.handlePacket(byteBuffer);
    }

    private void handlePacket(final ByteBuffer byteBuffer) {
        final PacketDirectionEnum packetDirection = this.session.getPacketDirection(ip);

        final CryptSession cryptSession = session.getCryptSession();
        if (cryptSession.getCryptState() != CryptState.CRYPTED) {
            handleCryptSession(byteBuffer, packetDirection, cryptSession);
            return;
        }

        final AbstractPacket associatedPacket = null;
        switch (packetDirection) {
            case CLIENT_TO_SERVER:
                cryptSession.decrypt(byteBuffer);
            break;

            case SERVER_TO_CLIENT:
                cryptSession.encrypt(byteBuffer);
            break;
        }

        // Compute the packet
        byteBuffer.getShort(); // size of packet
        final short opcode = byteBuffer.getShort();
        byteBuffer.position(0);
        final byte[] datas = new byte[byteBuffer.remaining()];
        byteBuffer.get(datas);
        final Packet packet = new Packet(opcode, packetDirection, datas);

        receivedPackets.put(opcode, packet);

        // Handler packet
        for (final AbstractPacketHandler packetHandler : packetHandlers) {
            packetHandler.handle(packet);
        }
    }

    private void handleCryptSession(final ByteBuffer byteBuffer, final PacketDirectionEnum packetDirection, final CryptSession cryptSession) {
        final byte[] datas = byteBuffer.array();
        if (datas.length != 128) {
            // This change the crypt state to KEY1
            cryptSession.sendKeyPacket();
            return;
        }

        switch (packetDirection) {
            case CLIENT_TO_SERVER:
                cryptSession.readClientKeyPacket(datas);
            break;

            case SERVER_TO_CLIENT:
                cryptSession.readServerKeyPacket(datas);
            break;
        }
    }
}
