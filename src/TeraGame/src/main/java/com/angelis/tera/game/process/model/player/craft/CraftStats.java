package com.angelis.tera.game.process.model.player.craft;

import java.util.Set;

import javolution.util.FastSet;

import com.angelis.tera.game.process.model.player.craft.enums.CraftTypeEnum;

public class CraftStats {
    
    public static final int MAX_LEVEL = 410;
    
    public final Set<Craft> crafts;
    
    public CraftStats(final Set<Craft> craftStatInfos) {
        this.crafts = craftStatInfos;
    }
    
    public CraftStats() {
        this.crafts = new FastSet<>();
        this.crafts.add(new Craft(CraftTypeEnum.WEAPON_SMITHING, 1));
        this.crafts.add(new Craft(CraftTypeEnum.FOCUS_CRAFTING, 1));
        this.crafts.add(new Craft(CraftTypeEnum.ARMOR_SMITHING, 1));
        this.crafts.add(new Craft(CraftTypeEnum.LEATHER_WORKING, 1));
        this.crafts.add(new Craft(CraftTypeEnum.TAILORING, 1));
        this.crafts.add(new Craft(CraftTypeEnum.ALCHEMY, 1));
        this.crafts.add(new Craft(CraftTypeEnum.RUNES, 1));
    }

    public Set<Craft> getCrafts() {
        return this.crafts;
    }
    
    public Integer getCraftLevel(final CraftTypeEnum craftType) {
        return this.getCraftStatInfo(craftType).getLevel();
    }
    
    public void setCraftLevel(final CraftTypeEnum craftType, int level) {
        if (level < 1) {
            level = 1;
        }
        
        if (level > MAX_LEVEL) {
            level = MAX_LEVEL;
        }
        
        this.getCraftStatInfo(craftType).setLevel(level);
    }
    
    public void processCraft(final CraftTypeEnum craftType) {
        this.setCraftLevel(craftType, this.getCraftLevel(craftType)+1);
    }
    
    private Craft getCraftStatInfo(final CraftTypeEnum craftType) {
        for (final Craft craftStatInfo : crafts) {
            if (craftStatInfo.getCraftType() == craftType) {
                return craftStatInfo;
            }
        }
        
        return null;
    }
}
