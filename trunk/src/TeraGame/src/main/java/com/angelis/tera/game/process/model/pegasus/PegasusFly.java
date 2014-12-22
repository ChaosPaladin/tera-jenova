package com.angelis.tera.game.process.model.pegasus;

import com.angelis.tera.game.process.model.AbstractTeraModel;

public class PegasusFly extends AbstractTeraModel {

    private int cost;
    private int fromNameId;
    private int toNameId;
    private int level;
    private int changeMapTickCount;
    private int endFlyTickCount;

    public PegasusFly(final Integer id) {
        super(id);
    }

    public int getCost() {
        return cost;
    }

    public void setCost(final int cost) {
        this.cost = cost;
    }

    public int getFromNameId() {
        return fromNameId;
    }

    public void setFromNameId(final int fromNameId) {
        this.fromNameId = fromNameId;
    }

    public int getToNameId() {
        return toNameId;
    }

    public void setToNameId(final int toNameId) {
        this.toNameId = toNameId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(final int level) {
        this.level = level;
    }

    public int getChangeMapTickCount() {
        return changeMapTickCount;
    }

    public void setChangeMapTickCount(final int changeMapTickCount) {
        this.changeMapTickCount = changeMapTickCount;
    }

    public int getEndFlyTickCount() {
        return endFlyTickCount;
    }
    
    public void setEndFlyTickCount(final int endFlyTickCount) {
        this.endFlyTickCount = endFlyTickCount;
    }
}
