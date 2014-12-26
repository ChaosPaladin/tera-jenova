package com.angelis.tera.packet.process.captors;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

import org.apache.log4j.Logger;
import org.jnetpcap.packet.Payload;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.PcapPacketHandler;
import org.jnetpcap.protocol.network.Ip4;

import com.angelis.tera.common.network.crypt.CryptSession;
import com.angelis.tera.common.network.crypt.CryptState;
import com.angelis.tera.packet.process.Session;
import com.angelis.tera.packet.process.enums.PacketDirectionEnum;

public abstract class AbstractCaptor implements PcapPacketHandler<String> {

    /** LOGGER */
    private static Logger log = Logger.getLogger(AbstractCaptor.class.getName());
    
    protected Session session;
    private final Ip4 ip = new Ip4();
    private final Payload payload = new Payload();

    @Override
    public void nextPacket(final PcapPacket packet, final String dummy) {
        if (!packet.hasHeader(ip)) {
            log.info("Ignored packet");
            return;
        }

        if (this.session == null) {
            this.session = new Session();
            try {
                this.session.setLocalAddress(InetAddress.getByAddress(ip.source()));
            }
            catch (final UnknownHostException e) {
                e.printStackTrace();
            }
        }
        
        if (!packet.hasHeader(payload)) {
            return;
        }
        
        this.handlePacket(ByteBuffer.wrap(payload.getByteArray(0, payload.size())));
    }
    
    private void handlePacket(final ByteBuffer byteBuffer) {
        final PacketDirectionEnum packetDirection = getPacketDirection();

        final CryptSession cryptSession = session.getCryptSession();
        if (cryptSession.getCryptState() != CryptState.CRYPTED) {
            final byte[] datas = byteBuffer.array();
            if (datas.length != 128) {
                return;
            }

            switch (packetDirection) {
                case CLIENT_SERVER:
                    if (session.clientKey1 == null) {
                        session.clientKey1 = datas;
                    } else {
                        session.clientKey2 = datas;
                    }
                break;
                
                case SERVER_CLIENT:
                    if (session.serverKey1 == null) {
                        session.serverKey1 = datas;
                    } else {
                        session.serverKey2 = datas;
                        session.initCryptSession();
                    }
                break;
            }
            return;
        }

        switch (packetDirection) {
            case CLIENT_SERVER:
                cryptSession.decrypt(byteBuffer);
            break;
            
            case SERVER_CLIENT:
                cryptSession.encrypt(byteBuffer);
            break;
        }
        this.handlePacket(byteBuffer, packetDirection);
    }
    
    private PacketDirectionEnum getPacketDirection() {
        try {
            return isClientAddress(InetAddress.getByAddress(ip.source())) ? PacketDirectionEnum.CLIENT_SERVER : PacketDirectionEnum.SERVER_CLIENT;
        }
        catch (final UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isClientAddress(final InetAddress inetAddress) {
        return inetAddress.equals(this.session.getLocalAddress());
    }

    public abstract void handlePacket(final ByteBuffer byteBuffer, PacketDirectionEnum packetDirection);
}
