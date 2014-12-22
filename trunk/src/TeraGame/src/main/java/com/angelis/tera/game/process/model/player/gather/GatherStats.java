package com.angelis.tera.game.process.model.player.gather;

import java.util.Set;

import javolution.util.FastSet;

import com.angelis.tera.game.process.model.player.gather.enums.GatherTypeEnum;

public class GatherStats {

    public final static int MAX_LEVEL = 300;

    public final Set<GatherStatInfo> gatherStatInfos;

    public GatherStats(final Set<GatherStatInfo> gatherStatInfos) {
        this.gatherStatInfos = gatherStatInfos;
    }

    public GatherStats() {
        this.gatherStatInfos = new FastSet<>();
        this.gatherStatInfos.add(new GatherStatInfo(GatherTypeEnum.ESSENCE, 1));
        this.gatherStatInfos.add(new GatherStatInfo(GatherTypeEnum.PLANT, 1));
        this.gatherStatInfos.add(new GatherStatInfo(GatherTypeEnum.BUG, 1));
        this.gatherStatInfos.add(new GatherStatInfo(GatherTypeEnum.MINE, 1));
    }

    public Set<GatherStatInfo> getGatherStatInfos() {
        return gatherStatInfos;
    }

    public Integer getGatherLevel(final GatherTypeEnum gatherType) {
        return this.getGatherStatInfo(gatherType).getLevel();
    }

    public void setGatherLevel(final GatherTypeEnum gatherType, int level) {
        if (level < 1) {
            level = 1;
        }

        if (level > GatherStats.MAX_LEVEL) {
            level = GatherStats.MAX_LEVEL;
        }

        this.getGatherStatInfo(gatherType).setLevel(level);
    }
    
    public void processGather(final GatherTypeEnum gatherType) {
        this.setGatherLevel(gatherType, this.getGatherLevel(gatherType)+1);
    }
    
    private GatherStatInfo getGatherStatInfo(final GatherTypeEnum gatherType) {
        for (final GatherStatInfo gatherStatInfo : gatherStatInfos) {
            if (gatherStatInfo.getGatherType() == gatherType) {
                return gatherStatInfo;
            }
        }
        
        return null;
    }
}
