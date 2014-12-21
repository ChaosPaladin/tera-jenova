package com.angelis.tera.game.domain.entity.database;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.angelis.tera.common.domain.entity.database.AbstractDatabaseEntity;

@Entity
@Table(name = "zones")
public class ZoneEntity extends AbstractDatabaseEntity {

    private static final long serialVersionUID = -6355207121827306999L;

    @Column()
    private byte[] datas;


    public ZoneEntity(final Integer id) {
        super(id);
    }
    
    public ZoneEntity() {
    }

    public byte[] getDatas() {
        return datas;
    }

    public void setDatas(final byte[] datas) {
        this.datas = datas;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        final int result = prime * Arrays.hashCode(datas);
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ZoneEntity)) {
            return false;
        }
        final ZoneEntity other = (ZoneEntity) obj;
        if (!Arrays.equals(datas, other.datas)) {
            return false;
        }
        return true;
    }
}
