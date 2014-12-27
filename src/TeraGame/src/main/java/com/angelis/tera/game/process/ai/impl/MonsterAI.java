package com.angelis.tera.game.process.ai.impl;

import com.angelis.tera.game.process.ai.AI;
import com.angelis.tera.game.process.model.creature.Monster;

public class MonsterAI extends AI<Monster> {

    public MonsterAI(final Monster visibleTeraObject) {
        super(visibleTeraObject);
    }
}
