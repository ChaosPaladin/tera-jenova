package com.angelis.tera.game.process.model.creature;

import java.util.Map;

import javolution.util.FastMap;

import com.angelis.tera.game.process.model.visible.VisibleTeraObject;

public class AggroList {
    public final Map<VisibleTeraObject, Integer> visibleTeraCreatures = new FastMap<>();
    
    public boolean isInAggroList(final VisibleTeraObject teraCreature) {
        return this.visibleTeraCreatures.containsKey(teraCreature);
    }
    
    public void addAggro(final VisibleTeraObject target) {
        this.visibleTeraCreatures.put(target, 0);
    }
    
    public void increaseAggro(final VisibleTeraObject teraCreature) {
        Integer aggro = this.visibleTeraCreatures.get(teraCreature);
        if (aggro == null) {
            aggro = 0;
        }
        
        this.visibleTeraCreatures.put(teraCreature, ++aggro);
    }

    public void clear() {
        this.visibleTeraCreatures.clear();
    }
}
