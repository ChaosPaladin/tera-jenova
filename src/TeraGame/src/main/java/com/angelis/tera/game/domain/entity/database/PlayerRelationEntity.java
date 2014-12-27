package com.angelis.tera.game.domain.entity.database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.angelis.tera.common.domain.entity.database.AbstractDatabaseEntity;

@Entity
@Table(name = "player_relations")
public class PlayerRelationEntity extends AbstractDatabaseEntity {

    private static final long serialVersionUID = 6582202985711800117L;

    @Column(name = "note")
    private String note;

    @Column(name = "friend")
    private boolean friend;

    @OneToOne
    private PlayerEntity owner;

    @OneToOne
    private PlayerEntity target;

    public PlayerRelationEntity(final int id) {
        super(id);
    }

    public PlayerRelationEntity() {

    }

    public String getNote() {
        return note;
    }

    public void setNote(final String note) {
        this.note = note;
    }

    public boolean isFriend() {
        return friend;
    }

    public void setFriend(final boolean friend) {
        this.friend = friend;
    }
    
    public PlayerEntity getOwner() {
        return owner;
    }

    public void setOwner(final PlayerEntity owner) {
        this.owner = owner;
    }

    public PlayerEntity getTarget() {
        return target;
    }

    public void setTarget(final PlayerEntity target) {
        this.target = target;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = prime * ((note == null) ? 0 : note.hashCode());
        result = prime * result + ((friend) ? 1 : 0);
        result = prime * result + ((owner == null) ? 0 : owner.hashCode());
        result = prime * result + ((target == null) ? 0 : target.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PlayerRelationEntity)) {
            return false;
        }
        final PlayerRelationEntity other = (PlayerRelationEntity) obj;
        if (note == null) {
            if (other.note != null) {
                return false;
            }
        }
        else if (!note.equals(other.note)) {
            return false;
        }
        if (friend != other.friend) {
            return false;
        }
        if (owner == null) {
            if (other.owner != null) {
                return false;
            }
        }
        else if (!owner.equals(other.owner)) {
            return false;
        }
        if (target == null) {
            if (other.target != null) {
                return false;
            }
        }
        else if (!target.equals(other.target)) {
            return false;
        }
        return true;
    }
}
