package com.angelis.tera.game.process.model.player;

import com.angelis.tera.common.process.model.AbstractModel;

public class PlayerRelation extends AbstractModel {
    
    private Player target;
    private String note;
    private boolean friend;

    public PlayerRelation(final Player target, final boolean friend) {
        super(null);
        this.target = target;
        this.friend = friend;
    }

    public PlayerRelation() {
        super(null);
    }

    public Player getTarget() {
        return target;
    }

    public void setTarget(final Player target) {
        this.target = target;
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

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof PlayerRelation)) {
            return false;
        }
        
        final PlayerRelation relation = (PlayerRelation) o;
        if (!this.target.equals(relation.getTarget())) {
            return false;
        }
        
        return this.friend = relation.friend;
    }
}
