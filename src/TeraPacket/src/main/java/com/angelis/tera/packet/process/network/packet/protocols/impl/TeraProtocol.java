package com.angelis.tera.packet.process.network.packet.protocols.impl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;

import javolution.util.FastList;
import javolution.util.FastMap;

import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.protocol.network.Ip4;

import com.angelis.tera.common.network.crypt.CryptSession;
import com.angelis.tera.common.network.crypt.CryptState;
import com.angelis.tera.game.presentation.network.packet.ClientPacketHandler;
import com.angelis.tera.game.presentation.network.packet.ServerPacketHandler;
import com.angelis.tera.packet.process.Session;
import com.angelis.tera.packet.process.network.packet.Packet;
import com.angelis.tera.packet.process.network.packet.enums.PacketDirectionEnum;
import com.angelis.tera.packet.process.network.packet.handlers.AbstractPacketHandler;
import com.angelis.tera.packet.process.network.packet.handlers.PacketHandlerEnum;
import com.angelis.tera.packet.process.network.packet.protocols.IProtocol;
import com.google.inject.Singleton;

@Singleton
public class TeraProtocol implements IProtocol {

    private Session session;
    private List<AbstractPacketHandler> packetHandlers;

    private final Map<Short, Packet> receivedPackets = new FastMap<>();
    private final List<Packet> creatureSpawns = new FastList<>();
    private final List<Packet> gatherSpawns = new FastList<>();

    private Packet tempPacket;
    private int remaining;

    public TeraProtocol() {
        ClientPacketHandler.init();
        ServerPacketHandler.init();
    }

    @Override
    public void nextPacket(final PcapPacket packet, final Ip4 ip) {
        if (this.session == null) {
            this.session = new Session();
            try {
                // First packet is inited by client
                this.session.setLocalAddress(InetAddress.getByAddress(ip.source()));
            }
            catch (final UnknownHostException e) {
            }
        }
    }

    @Override
    public Packet handlePacket(final ByteBuffer byteBuffer, final Ip4 ip) {
        final PacketDirectionEnum packetDirection = this.session.getPacketDirection(ip);

        final CryptSession cryptSession = session.getCryptSession();
        if (cryptSession.getCryptState() != CryptState.CRYPTED) {
            handleCryptSession(byteBuffer, packetDirection, cryptSession);
            return null;
        }

        handleCrypt(byteBuffer, packetDirection);

        Packet packet = null;
        if (tempPacket != null && tempPacket.getPacketDirection() == packetDirection) {
            packet = handleOldPacket(byteBuffer, packetDirection);
            tempPacket = null;
        }
        else {
            packet = handleNewPacket(byteBuffer, packetDirection);
        }
        
        if (packet != null) {
            switch (packetDirection) {
                case CLIENT_TO_SERVER:
                    ClientPacketHandler.getClientPacket(packet.getOpcode());
                break;
    
                case SERVER_TO_CLIENT:
                    
                break;
            }
        }

        return packet;
    }

    private void handleCrypt(final ByteBuffer byteBuffer, final PacketDirectionEnum packetDirection) {
        switch (packetDirection) {
            case CLIENT_TO_SERVER:
                session.getCryptSession().decrypt(byteBuffer);
            break;

            case SERVER_TO_CLIENT:
                session.getCryptSession().encrypt(byteBuffer);
            break;
        }
    }

    private Packet handleNewPacket(final ByteBuffer byteBuffer, final PacketDirectionEnum packetDirection) {
        // Compute the packet
        final int size = byteBuffer.getShort();
        final short opcode = byteBuffer.getShort();
        
        byteBuffer.position(0);
        final int realPacketSize = byteBuffer.remaining();
        final byte[] datas = new byte[realPacketSize];
        byteBuffer.get(datas);
        
        Packet packet = null;
        if (size > realPacketSize) {
            // we didn't received all the content
            tempPacket = new Packet(opcode, packetDirection, datas);
            receivedPackets.put(opcode, packet);
        } else {
            packet = new Packet(opcode, packetDirection, datas);
        }

        return packet;
    }

    private Packet handleOldPacket(final ByteBuffer byteBuffer, final PacketDirectionEnum packetDirection) {
        final byte[] datas = new byte[byteBuffer.remaining()];
        byteBuffer.get(datas);

        final byte[] combined = new byte[tempPacket.getDatas().length + datas.length];
        System.arraycopy(tempPacket.getDatas(), 0, combined, 0, tempPacket.getDatas().length);
        System.arraycopy(datas, 0, combined, tempPacket.getDatas().length, datas.length);

        return new Packet(tempPacket.getOpcode(), packetDirection, combined);
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

    @Override
    public EnumSet<PacketHandlerEnum> getPacketHandlers() {
        return EnumSet.of(PacketHandlerEnum.UI);
    }
}
