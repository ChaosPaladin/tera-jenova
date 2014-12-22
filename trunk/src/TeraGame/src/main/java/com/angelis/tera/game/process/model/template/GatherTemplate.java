package com.angelis.tera.game.process.model.template;

import com.angelis.tera.game.process.model.player.gather.enums.GatherTypeEnum;

public class GatherTemplate extends Template {

    private GatherTypeEnum gatherType;
    private int minLevel;
    private int maxLevel;
    private int gatherCostPoint;

    public GatherTypeEnum getGatherType() {
        return gatherType;
    }

    public void setGatherType(final GatherTypeEnum gatherType) {
        this.gatherType = gatherType;
    }

    public int getMinLevel() {
        return minLevel;
    }

    public void setMinLevel(final int minLevel) {
        this.minLevel = minLevel;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(final int maxLevel) {
        this.maxLevel = maxLevel;
    }

    public int getGatherCostPoint() {
        return gatherCostPoint;
    }

    public void setGatherCostPoint(final int gatherCostPoint) {
        this.gatherCostPoint = gatherCostPoint;
    }

}
