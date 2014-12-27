package com.angelis.tera.game.process.model.mount;

import com.angelis.tera.common.process.model.AbstractModel;

public final class Mount extends AbstractModel {

    private int skillId;
    private int speed;

    public Mount(final int id) {
        super(id);
    }

    public int getSkillId() {
        return skillId;
    }

    public void setSkillId(final int skillId) {
        this.skillId = skillId;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(final int speed) {
        this.speed = speed;
    }
}
