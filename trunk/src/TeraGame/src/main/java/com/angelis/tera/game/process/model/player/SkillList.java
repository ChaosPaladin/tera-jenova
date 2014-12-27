package com.angelis.tera.game.process.model.player;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javolution.util.FastMap;
import javolution.util.FastSet;

import com.angelis.tera.game.process.model.skill.Skill;

public class SkillList {

    public final Map<Skill, Integer> skillLevels = new FastMap<>();

    public SkillList(final Set<Skill> skills) {
        for (final Skill skill : skills) {
            skillLevels.put(skill, skill.getLevel());
        }
    }

    public SkillList() {
        this(new FastSet<>());
    }

    public int size() {
        return skillLevels.size();
    }

    public Set<Skill> getSkills() {
        return this.skillLevels.keySet();
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
