package com.angelis.tera.game.process.model.player.craft;

import com.angelis.tera.game.process.model.AbstractTeraModel;
import com.angelis.tera.game.process.model.player.craft.enums.CraftTypeEnum;

public class Craft extends AbstractTeraModel {

    private CraftTypeEnum craftType;
    private int level;
    
    public Craft(final Integer id) {
        super(id);
    }
    
    public Craft() {
        this(null);
    }
    
    public Craft(final CraftTypeEnum craftType, final int level) {
        this(null);
        this.craftType = craftType;
        this.level = level;
    }

    public CraftTypeEnum getCraftType() {
        return craftType;
    }

    public void setCraftType(final CraftTypeEnum craftType) {
        this.craftType = craftType;
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
        result = prime * result + ((craftType == null) ? 0 : craftType.hashCode());
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
        if (!(obj instanceof Craft)) {
            return false;
        }
        final Craft other = (Craft) obj;
        if (craftType != other.craftType) {
            return false;
        }
        if (level != other.level) {
            return false;
        }
        return true;
    }
}
