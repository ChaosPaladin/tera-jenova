package com.angelis.tera.game.process.model.creature;

import java.util.Set;

import javolution.util.FastSet;

import com.angelis.tera.game.process.controllers.Controller;
import com.angelis.tera.game.process.model.abnormality.Abnormality;
import com.angelis.tera.game.process.model.template.Template;
import com.angelis.tera.game.process.model.visible.VisibleTeraObject;
import com.angelis.tera.game.process.services.BaseStatService;


public abstract class AbstractCreature extends VisibleTeraObject {
    
    private CurrentStats currentStats;
    private CreatureBonusStats creatureBonusStats;
    private int level;
    private AbstractCreature target;
    private final Set<Abnormality> abnormalities = new FastSet<>();
    
    public AbstractCreature(final Integer id, final Controller<? extends AbstractCreature> controller, final Template template) {
        super(id, controller, template);
    }

    public AbstractCreature(final AbstractCreature creature, final Controller<? extends AbstractCreature> controller) {
        super(creature, controller, creature.template);
        this.level = creature.level;
        BaseStatService.getInstance().affectCreatureCurrentStats(this);
    }

    public CurrentStats getCurrentStats() {
        return currentStats;
    }

    public void setCurrentStats(final CurrentStats currentStats) {
        this.currentStats = currentStats;
    }
    
    public CreatureBonusStats getCreatureBonusStats() {
        return creatureBonusStats;
    }

    public void setCreatureBonusStats(final CreatureBonusStats creatureBonusStats) {
        this.creatureBonusStats = creatureBonusStats;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(final int level) {
        this.level = level;
    }

    public AbstractCreature getTarget() {
        return target;
    }

    public void setTarget(final AbstractCreature target) {
        this.target = target;
    }
    
    public Set<Abnormality> getAbnormalities() {
        return abnormalities;
    }
}
