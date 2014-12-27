package com.angelis.tera.game.process.model.skill;

import com.angelis.tera.common.process.model.AbstractModel;

public class Skill extends AbstractModel {

    private int skillId;
    private int level;

    public Skill() {
        super(null);
    }
    
    public Skill(final Integer id) {
        super(id);
    }

    public int getSkillId() {
        return skillId;
    }
    
    public void setSkillId(final int skillId) {
        this.skillId = skillId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(final int level) {
        this.level = level;
    }
}
