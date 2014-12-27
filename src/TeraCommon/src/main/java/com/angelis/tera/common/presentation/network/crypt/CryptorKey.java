package com.angelis.tera.common.presentation.network.crypt;

public class CryptorKey {
    int size;
    int pos1;
    int pos2;
    int maxPos;
    int key;
    byte[] buffer;
    long sum;

    public CryptorKey(int pos, int size) {
        this.pos2 = pos;
        this.maxPos = pos;
        this.size = size;
        this.buffer = new byte[size * 4];
    }
}