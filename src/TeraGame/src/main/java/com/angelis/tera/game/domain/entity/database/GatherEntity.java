package com.angelis.tera.game.domain.entity.database;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.angelis.tera.common.domain.entity.database.AbstractDatabaseEntity;
import com.angelis.tera.game.process.model.player.gather.enums.GatherTypeEnum;

@Entity
@Table(name = "player_gathers")
public class GatherEntity extends AbstractDatabaseEntity {

    private static final long serialVersionUID = 1336700914293219033L;

    @Column
    private GatherTypeEnum gatherType;
    
    @Column
    private int level;
    
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "player_id", nullable = false)
    private PlayerEntity player;

    public GatherEntity(final Integer id) {
        super(id);
    }
    
    public GatherEntity() {
        super();
    }

    public GatherTypeEnum getGatherType() {
        return gatherType;
    }

    public void setGatherType(final GatherTypeEnum gatherType) {
        this.gatherType = gatherType;
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
        int result = prime * ((gatherType == null) ? 0 : gatherType.hashCode());
        result = prime * result + ((player == null) ? 0 : player.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof GatherEntity)) {
            return false;
        }
        final GatherEntity other = (GatherEntity) obj;
        if (gatherType != other.gatherType) {
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
