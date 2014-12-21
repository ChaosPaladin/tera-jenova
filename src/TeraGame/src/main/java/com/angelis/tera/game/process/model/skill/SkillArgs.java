package com.angelis.tera.game.process.model.skill;

import com.angelis.tera.common.utils.Point3D;
import com.angelis.tera.game.process.model.visible.WorldPosition;

public class SkillArgs {
    
    private int skillId;
    private WorldPosition startPosition;
    private Point3D endPosition;
    
    public int getSkillId() {
        return skillId;
    }

    public void setSkillId(final int skillId) {
        this.skillId = skillId;
    }

    public WorldPosition getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(final WorldPosition startPosition) {
        this.startPosition = startPosition;
    }

    public Point3D getEndPosition() {
        return endPosition;
    }

    public void setEndPosition(final Point3D endPosition) {
        this.endPosition = endPosition;
    }
}
