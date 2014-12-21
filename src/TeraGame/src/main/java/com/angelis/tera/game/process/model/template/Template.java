package com.angelis.tera.game.process.model.template;

import com.angelis.tera.common.process.model.AbstractModel;
import com.angelis.tera.game.process.model.creature.BaseStats;

public abstract class Template extends AbstractModel {

    private BaseStats baseStats;
    
    public Template() {
        super(null);
    }

    public BaseStats getBaseStats() {
        return baseStats;
    }

    public void setBaseStats(final BaseStats baseStats) {
        this.baseStats = baseStats;
    }
}
