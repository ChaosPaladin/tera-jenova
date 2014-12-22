package com.angelis.tera.game.domain.entity.database;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.angelis.tera.common.domain.entity.database.AbstractDatabaseEntity;
import com.angelis.tera.game.process.model.player.craft.enums.CraftTypeEnum;

@Entity
@Table(name = "player_crafts")
public class CraftEntity extends AbstractDatabaseEntity {

    private static final long serialVersionUID = -5556823341963292068L;

    @Column
    private CraftTypeEnum craftType;
    
    @Column
    private int level;
    
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "player_id", nullable = false)
    private PlayerEntity player;

    public CraftEntity(final Integer id) {
        super(id);
    }
    
    public CraftEntity() {
        super();
    }

    public CraftTypeEnum getCraftType() {
        return craftType;
    }

    public void setCraftType(final CraftTypeEnum craftType) {
        this.craftType = craftType;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(final int level) {
        this.level = level;
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
        int result = prime * ((craftType == null) ? 0 : craftType.hashCode());
        result = prime * result + ((player == null) ? 0 : player.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CraftEntity)) {
            return false;
        }
        final CraftEntity other = (CraftEntity) obj;
        if (craftType != other.craftType) {
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
