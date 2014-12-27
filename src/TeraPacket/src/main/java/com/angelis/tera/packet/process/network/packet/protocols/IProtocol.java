package com.angelis.tera.packet.process.network.packet.protocols;

import java.nio.ByteBuffer;
import java.util.EnumSet;

import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.protocol.network.Ip4;

import com.angelis.tera.packet.process.network.packet.Packet;
import com.angelis.tera.packet.process.network.packet.handlers.PacketHandlerEnum;

public interface IProtocol {
    void nextPacket(PcapPacket packet, Ip4 ip);
    Packet handlePacket(ByteBuffer byteBuffer, Ip4 ip);
    EnumSet<PacketHandlerEnum> getPacketHandlers();
}
