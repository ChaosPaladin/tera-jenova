package com.angelis.tera.game.process.model.template;

import com.angelis.tera.game.process.model.creature.enums.RankEnum;
import com.angelis.tera.game.process.model.enums.CreatureTitleEnum;

public class CreatureTemplate extends Template {

    private int fullId;
    private RankEnum rank;
    private int exp;
    private int huntingZoneId;
    private int modelId;
    private boolean offensive;
    private CreatureTitleEnum creatureTitle;

    public int getFullId() {
        return fullId;
    }

    public void setFullId(final int fullId) {
        this.fullId = fullId;
    }

    public RankEnum getRank() {
        return rank;
    }

    public void setRank(final RankEnum rank) {
        this.rank = rank;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(final int exp) {
        this.exp = exp;
    }

    public int getHuntingZoneId() {
        return huntingZoneId;
    }

    public void setHuntingZoneId(final int huntingZoneId) {
        this.huntingZoneId = huntingZoneId;
    }

    public int getModelId() {
        return modelId;
    }

    public void setModelId(final int modelId) {
        this.modelId = modelId;
    }

    public boolean isOffensive() {
        return offensive;
    }

    public void setOffensive(final boolean offensive) {
        this.offensive = offensive;
    }

    public CreatureTitleEnum getCreatureTitle() {
        return creatureTitle;
    }

    public void setCreatureTitle(final CreatureTitleEnum creatureTitle) {
        this.creatureTitle = creatureTitle;
    }
}
