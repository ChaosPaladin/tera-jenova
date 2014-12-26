package com.angelis.tera.packet.process;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.jnetpcap.protocol.network.Ip4;

import com.angelis.tera.common.network.crypt.CryptSession;
import com.angelis.tera.packet.process.network.packet.enums.PacketDirectionEnum;

public class Session {
    protected final CryptSession cryptSession;
    private InetAddress localAddress;

    public byte[] clientKey1 = null;
    public byte[] clientKey2 = null;
    public byte[] serverKey1 = null;
    public byte[] serverKey2 = null;

    public Session() {
        this.cryptSession = new CryptSession();
    }

    public CryptSession getCryptSession() {
        return cryptSession;
    }

    public void initCryptSession() {
        this.cryptSession.initKeys(clientKey1, clientKey2, serverKey1, serverKey2);
    }

    public void setLocalAddress(final InetAddress localAddress) {
        this.localAddress = localAddress;
    }

    public InetAddress getLocalAddress() {
        return this.localAddress;
    }

    public boolean isClientPacket(final Ip4 ip) {
        try {
            return InetAddress.getByAddress(ip.source()).equals(this.localAddress);
        } catch (final UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    public PacketDirectionEnum getPacketDirection(final Ip4 ip) {
        return isClientPacket(ip) ? PacketDirectionEnum.CLIENT_TO_SERVER : PacketDirectionEnum.SERVER_TO_CLIENT;
    }
}
