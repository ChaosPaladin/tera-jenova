package com.angelis.tera.common.presentation.network.crypt;

import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Set;

import javolution.util.FastSet;

import org.apache.log4j.Logger;

import com.angelis.tera.common.lang.EmptyEventable;
import com.angelis.tera.common.lang.IsObservable;
import com.angelis.tera.common.lang.IsObserver;
import com.angelis.tera.common.utils.CryptoUtils;

/**
 * @author Angelis
 */
public final class CryptSession implements IsObservable<EmptyEventable> {

    /** LOGGER */
    private static final Logger log = Logger.getLogger(CryptSession.class);

    protected Cryptor encryptor = new Cryptor();
    protected Cryptor decryptor = new Cryptor();

    public byte[] encryptKey = new byte[128];
    public byte[] decryptKey = new byte[128];

    public byte[] clientKey1 = new byte[128];
    public byte[] clientKey2 = new byte[128];
    public byte[] serverKey1 = CryptoUtils.Random128Key();
    public byte[] serverKey2 = CryptoUtils.Random128Key();

    public byte[] tmpKey1 = new byte[128];
    public byte[] tmpKey2 = new byte[128];

    private CryptState cryptState = CryptState.NONE;

    protected final Set<IsObserver<EmptyEventable>> observers = new FastSet<>();

    private boolean inited;

    public CryptSession() {
    }

    public void initKeys(final byte[] clientKey1, final byte[] clientKey2, final byte[] serverKey1, final byte[] serverKey2) {
        this.clientKey1 = clientKey1;
        this.clientKey2 = clientKey2;

        this.serverKey1 = serverKey1;
        this.serverKey2 = serverKey2;
        
        this.initCryptors();
        this.cryptState = CryptState.CRYPTED;
    }

    private final void initCryptors() {
        final byte[] tmp = new byte[128];
        final byte[] tmp2 = new byte[128];
        final byte[] crypt_key_1 = new byte[128];
        byte[] crypt_key_2 = new byte[128];

        CryptoUtils.shiftKey(serverKey1, tmp, 31, true);
        CryptoUtils.xorKey(tmp, clientKey1, tmp2);
        CryptoUtils.shiftKey(clientKey2, tmp, 17, false);
        CryptoUtils.xorKey(tmp, tmp2, crypt_key_1);

        decryptor.generateKey(crypt_key_1);
        CryptoUtils.shiftKey(serverKey2, tmp, 79, true);
        decryptor.applyCryptor(tmp, 128);
        crypt_key_2 = tmp;
        encryptor.generateKey(crypt_key_2);

        log.debug("CryptSession inited");
        this.inited = true;
    }

    public final void readClientKeyPacket(final byte[] datas) {
        switch (cryptState) {
            case KEY1:
                this.clientKey1 = datas;
            break;

            case KEY2:
                this.clientKey2 = datas;
            break;

            default:
                throw new RuntimeException("You tryed to read key packet while having " + this.cryptState.name() + " crypt state");
        }
    }
    
    public final void readServerKeyPacket(final byte[] datas) {
        switch (cryptState) {
            case KEY1:
                this.serverKey1 = datas;
            break;

            case KEY2:
                this.serverKey2 = datas;
            break;

            default:
                throw new RuntimeException("You tryed to read key packet while having " + this.cryptState.name() + " crypt state");
        }
        
        updateState();
    }

    public final byte[] sendKeyPacket() {
        byte[] keyPacket = null;

        switch (this.cryptState) {
            case NONE:
                keyPacket = new byte[] { 1, 0, 0, 0 };
            break;

            case KEY1:
                keyPacket = this.serverKey1;
            break;

            case KEY2:
                keyPacket = this.serverKey2;
            break;

            default:
                throw new RuntimeException("You tryed to write key packet while having " + this.cryptState.name() + " crypt state");
        }

        updateState();
        return keyPacket;
    }

    public final void decrypt(final byte[] data, final int size) {
        if (this.cryptState != CryptState.CRYPTED) {
            return;
        }

        if (!this.inited) {
            this.initCryptors();
        }

        decryptor.applyCryptor(data, size);
    }

    public final void decrypt(final ByteBuffer buffer) {
        this.decrypt(buffer.array(), buffer.remaining());
    }

    public final void encrypt(final byte[] data, final int size) {
        if (this.cryptState != CryptState.CRYPTED) {
            return;
        }

        if (!this.inited) {
            this.initCryptors();
        }

        encryptor.applyCryptor(data, size);
    }

    public final void encrypt(final ByteBuffer buffer) {
        this.encrypt(buffer.array(), buffer.remaining());
    }

    public final void disableCrypt() {
        this.cryptState = CryptState.DISABLED;
    }

    public boolean isCryptEnabled() {
        return this.cryptState != CryptState.DISABLED;
    }

    public CryptState getCryptState() {
        return this.cryptState;
    }

    private final void updateState() {
        switch (this.cryptState) {
            case NONE:
                this.cryptState = CryptState.KEY1;
            break;

            case KEY1:
                this.cryptState = CryptState.KEY2;
            break;

            case KEY2:
                this.cryptState = CryptState.CRYPTED;
            break;

            default:
                throw new RuntimeException("Trying to update state while state is " + this.cryptState.name());
        }

        this.notifyObservers(EmptyEventable.NONE, this.cryptState);
    }

    @Override
    public void addObserver(final IsObserver<EmptyEventable> observer) {
        this.observers.add(observer);
    }

    @Override
    public void notifyObservers(final EmptyEventable event, final Object... data) {
        for (final IsObserver<EmptyEventable> observer : this.observers) {
            observer.onObservableUpdate(event, this, data);
        }
    }

    @Override
    public Collection<IsObserver<EmptyEventable>> getObservers() {
        return this.observers;
    }
}
