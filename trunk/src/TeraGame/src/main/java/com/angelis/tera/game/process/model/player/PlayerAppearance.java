package com.angelis.tera.game.process.model.player;

public final class PlayerAppearance {
    
    private byte[] data;
    private byte[] details1;
    private byte[] details2;

    public PlayerAppearance() {
        super();
    }
    
    public byte[] getData() {
        return data;
    }

    public void setData(final byte[] data) {
        this.data = data;
    }

    public byte[] getDetails1() {
        return details1;
    }

    public void setDetails1(final byte[] details1) {
        this.details1 = details1;
    }

    public byte[] getDetails2() {
        return details2;
    }

    public void setDetails2(final byte[] details2) {
        this.details2 = details2;
    }
}
