package com.angelis.tera.game.process.model;

public class Zone extends AbstractTeraModel {
    
    private byte[] datas;

    public Zone(final Integer id) {
        super(id);
    }
    
    public Zone() {
        super(null);
    }

    public byte[] getDatas() {
        return datas;
    }

    public void setDatas(final byte[] datas) {
        this.datas = datas;
    }
}
