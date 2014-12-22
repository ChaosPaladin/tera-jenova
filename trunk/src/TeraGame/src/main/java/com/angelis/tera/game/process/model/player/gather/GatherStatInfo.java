package com.angelis.tera.game.process.model.player.gather;

import com.angelis.tera.game.process.model.AbstractTeraModel;
import com.angelis.tera.game.process.model.player.gather.enums.GatherTypeEnum;

public class GatherStatInfo extends AbstractTeraModel {

    private GatherTypeEnum gatherType;
    private int level;

    public GatherStatInfo(final Integer id) {
        super(id);
    }
    
    public GatherStatInfo(final GatherTypeEnum gatherType, final int level) {
        this(null);
        this.gatherType = gatherType;
        this.level = level;
    }

    public GatherTypeEnum getGatherType() {
        return gatherType;
    }
    
    public void setGatherType(final GatherTypeEnum gatherType) {
        this.gatherType = gatherType;
    }

    public int getLevel() {
        return level;
    }
    
    public void setLevel(final int level) {
        this.level = level;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((gatherType == null) ? 0 : gatherType.hashCode());
        result = prime * result + level;
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof GatherStatInfo)) {
            return false;
        }
        final GatherStatInfo other = (GatherStatInfo) obj;
        if (gatherType != other.gatherType) {
            return false;
        }
        if (level != other.level) {
            return false;
        }
        return true;
    }
}
