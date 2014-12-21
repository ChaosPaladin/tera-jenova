package com.angelis.tera.game.process.model.player;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javolution.util.FastMap;

import com.angelis.tera.game.process.model.skill.Skill;

public class SkillList {

    public final Map<Skill, Integer> skillLevels;

    public SkillList(final Map<Skill, Integer> skillLevels) {
        this.skillLevels = skillLevels;
    }

    public SkillList() {
        this(new FastMap<Skill, Integer>());
    }

    public int size() {
        return skillLevels.size();
    }

    public Set<Skill> getSkills() {
        return this.skillLevels.keySet();
    }

    public Map<Skill, Integer> getSkillLevels() {
        return skillLevels;
    }

    public void learnSkill(final Skill skill, final int level) {
        Integer actualLevel = this.skillLevels.get(skill);
        if (actualLevel == null) {
            actualLevel = 0;
        }

        this.skillLevels.put(skill, actualLevel + level);
    }

    public void learnSkill(final Skill skill) {
        this.learnSkill(skill, 1);
    }

    public void unlearnSkill(final Skill skill) {
        this.skillLevels.remove(skill);
    }

    public void addSkills(final List<Skill> skills) {
        for (final Skill skill : skills) {
            this.learnSkill(skill);
        }
    }

    public Skill getSkillById(final Integer skillId) {
        for (final Skill skill : this.skillLevels.keySet()) {
            if (skill.getSkillId() == skillId) {
                return skill;
            }
        }
        return null;
    }
}
