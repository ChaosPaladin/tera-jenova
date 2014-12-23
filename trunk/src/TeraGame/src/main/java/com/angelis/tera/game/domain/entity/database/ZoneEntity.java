package com.angelis.tera.game.domain.entity.database;

import java.util.Arrays;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.angelis.tera.common.domain.entity.database.AbstractDatabaseEntity;

@Entity
@Table(name = "player_zones")
public class ZoneEntity extends AbstractDatabaseEntity {

    private static final long serialVersionUID = -6355207121827306999L;

    @Column()
    private byte[] datas;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "visitedZones")
    private PlayerEntity player;

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

    public PlayerEntity getPlayer() {
        return player;
    }

    public void setPlayer(final PlayerEntity player) {
        this.player = player;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Arrays.hashCode(datas);
        result = prime * result + ((player == null) ? 0 : player.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof ZoneEntity)) {
            return false;
        }
        final ZoneEntity other = (ZoneEntity) obj;
        if (!Arrays.equals(datas, other.datas)) {
            return false;
        }
        if (player == null) {
            if (other.player != null) {
                return false;
            }
        }
        else if (!player.equals(other.player)) {
            return false;
        }
        return true;
    }
}
