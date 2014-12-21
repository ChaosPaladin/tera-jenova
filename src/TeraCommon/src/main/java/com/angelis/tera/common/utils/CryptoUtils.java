package com.angelis.tera.common.utils;

import java.util.Random;

public class CryptoUtils {

    protected static final Random r = new Random();

    public static void xorKey(byte[] src1, byte[] src2, byte[] dst) {
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 16; j++)
                dst[i * 16 + j] = (byte) ((src1[i * 16 + j] & 0xff) ^ (src2[i * 16 + j] & 0xff));
    }

    public static void shiftKey(byte[] src, byte[] dest, int n, boolean direction) {
        byte[] tmp = new byte[128];
        for (int i = 0; i < 128; i++) {
            if (direction)
                tmp[(i + n) % 128] = src[i];
            else
                tmp[i] = src[(i + n) % 128];
        }
        for (int i = 0; i < 128; i++)
            dest[i] = tmp[i];
    }

    public static byte[] Random128Key() {
        byte[] result = new byte[128];
        for (int i = 0; i < 128; i++)
            result[i] = (byte) Rnd.get(128);
        return result;
    }
}
