package com.angelis.tera.packet.process;

import java.net.InetAddress;

import com.angelis.tera.common.network.crypt.CryptSession;

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
}
