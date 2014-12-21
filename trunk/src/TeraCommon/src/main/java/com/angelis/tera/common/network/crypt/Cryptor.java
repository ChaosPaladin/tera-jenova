package com.angelis.tera.common.network.crypt;

import gnu.crypto.hash.Sha160;

import org.apache.commons.lang3.ArrayUtils;

public class Cryptor {
    
    private int changeData;
    private int changeLenght;
    private CryptorKey[] key = new CryptorKey[]{
        new CryptorKey(31, 55),
        new CryptorKey(50, 57),
        new CryptorKey(39, 58),
    };

    public void generateKey(byte[] k) {
        byte[] buf = new byte[680];
        fillKey(k, buf);
        Sha160 sha = new Sha160();
        for (int i = 0; i < 680; i += 20) {
            sha.update(buf, 0, 680);
            byte[] digest = sha.digest();
            for (int j = i, l = 0; j < i + 20; j++, l++) {
                buf[j] = digest[l];
            }
        }
        
        key[0].buffer = ArrayUtils.subarray(buf, 0, 220);
        key[1].buffer = ArrayUtils.subarray(buf, 220, 448);
        key[2].buffer = ArrayUtils.subarray(buf, 448, 680);
    }

    private void fillKey(byte[] src, byte[] dst) {
        for (int i = 0; i < 680; i++)
            dst[i] = src[i % 128];
        dst[0] = (byte) 128;
    }

    public void applyCryptor(byte[] buf, int size) {
        int pre = (size < changeLenght) ? size : changeLenght;
        if (pre != 0) {
            byte[] dat = new byte[4];
            writeIntToBytes(dat, changeData, 0);
            for (int j = 0; j < pre; j++)
                buf[j] ^= dat[4 - changeLenght + j];
            changeLenght -= pre;
            size -= pre;
        }

        int offset = pre;
        for (int i = 0; i < size / 4; i++) {
            int result = key[0].key & key[1].key | key[2].key & (key[0].key | key[1].key);
            for (int j = 0; j < 3; j++) {
                CryptorKey k = key[j];
                if (result == k.key) {
                    long t1 = forBytesToUInts(k.buffer, k.pos1 * 4);
                    long t2 = forBytesToUInts(k.buffer, k.pos2 * 4);
                    long t3 = (t1 <= t2) ? t1 : t2;
                    long tempLong = t1 + t2;
                    if (tempLong > 4294967295l)
                        k.sum = ((int) t1 + (int) t2) & 0xFFFFFFFFL;
                    else
                        k.sum = tempLong;
                    k.key = (t3 > k.sum) ? 1 : 0;
                    k.pos1 = (k.pos1 + 1) % k.size;
                    k.pos2 = (k.pos2 + 1) % k.size;
                }
                long unsBuf = forBytesToUInts(buf, offset + i * 4) ^ k.sum;
                writeIntToBytes(buf, unsBuf, offset + i * 4);
            }
        }
        int remain = size & 3;
        if (remain != 0) {
            int result = key[0].key & key[1].key | key[2].key & (key[0].key | key[1].key);
            changeData = 0;
            for (int j = 0; j < 3; j++) {
                CryptorKey k = key[j];
                if (result == k.key) {
                    long t1 = forBytesToUInts(k.buffer, k.pos1 * 4);
                    long t2 = forBytesToUInts(k.buffer, k.pos2 * 4);
                    long t3 = (t1 <= t2) ? t1 : t2;
                    long tempLong = t1 + t2;
                    if (tempLong > 4294967295l)
                        k.sum = ((int) t1 + (int) t2) & 0xFFFFFFFFL;
                    else
                        k.sum = tempLong;
                    k.key = (t3 > k.sum) ? 1 : 0;
                    k.pos1 = (k.pos1 + 1) % k.size;
                    k.pos2 = (k.pos2 + 1) % k.size;
                }
                changeData ^= k.sum;
            }
            byte[] dat = new byte[4];
            writeIntToBytes(dat, changeData, 0);
            for (int j = 0; j < remain; j++)
                buf[size + pre - remain + j] ^= dat[j];
            changeLenght = 4 - remain;
        }
    }

    private static void writeIntToBytes(byte[] buf, long unsignedInt, int offset) {
        buf[offset + 3] = (byte) ((unsignedInt & 0xFF000000L) >> 24);
        buf[offset + 2] = (byte) ((unsignedInt & 0x00FF0000L) >> 16);
        buf[offset + 1] = (byte) ((unsignedInt & 0x0000FF00L) >> 8);
        buf[offset] = (byte) ((unsignedInt & 0x000000FFL));
    }

    private static long forBytesToUInts(byte[] bytes, int start) {
        byte b1 = bytes[start + 3];
        byte b2 = bytes[start + 2];
        byte b3 = bytes[start + 1];
        byte b4 = bytes[start];
        int i = ((0xFF & b1) << 24) | ((0xFF & b2) << 16) | ((0xFF & b3) << 8) | (0xFF & b4);
        return i & 0xFFFFFFFFL;
    }

    class Key {
        

        
    }
}