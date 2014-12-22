package com.angelis.tera.game.process.model.player.gather;

import java.util.Set;

import javolution.util.FastSet;

import com.angelis.tera.game.process.model.player.gather.enums.GatherTypeEnum;

public class GatherStats {

    public final static int MAX_LEVEL = 300;

    public final Set<Gather> gathers;

    public GatherStats(final Set<Gather> gathers) {
        this.gathers = gathers;
    }

    public GatherStats() {
        this.gathers = new FastSet<>();
        this.gathers.add(new Gather(GatherTypeEnum.ESSENCE, 1));
        this.gathers.add(new Gather(GatherTypeEnum.PLANT, 1));
        this.gathers.add(new Gather(GatherTypeEnum.BUG, 1));
        this.gathers.add(new Gather(GatherTypeEnum.MINE, 1));
    }

    public Set<Gather> getGathers() {
        return gathers;
    }

    public Integer getGatherLevel(final GatherTypeEnum gatherType) {
        return this.getGather(gatherType).getLevel();
    }

    public void setGatherLevel(final GatherTypeEnum gatherType, int level) {
        if (level < 1) {
            level = 1;
        }

        if (level > GatherStats.MAX_LEVEL) {
            level = GatherStats.MAX_LEVEL;
        }

        this.getGather(gatherType).setLevel(level);
    }

    public void processGather(final GatherTypeEnum gatherType) {
        this.setGatherLevel(gatherType, this.getGatherLevel(gatherType) + 1);
    }

    private Gather getGather(final GatherTypeEnum gatherType) {
        Gather value = null;
        for (final Gather gather : gathers) {
            if (gather.getGatherType() == gatherType) {
                value = gather;
                break;
            }
        }

        return value;
    }
}
