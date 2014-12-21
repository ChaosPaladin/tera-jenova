package com.angelis.tera.game.process.model.player.craft.enums;

public enum CraftTypeEnum {
    WEAPON_SMITHING(1),
    FOCUS_CRAFTING(2),
    ARMOR_SMITHING(3),
    LEATHER_WORKING(4),
    TAILORING(5),
    ALCHEMY(6),
    RUNES(7),
    
    CLOTH_EXTRACTION(11),
    METAL_EXTRACTION(12),
    ALCHEMY_EXTRACTION(13),
    LEATHER_EXTRACTION(14)
    ;
    
    public final int value;
    
    CraftTypeEnum(final int value) {
        this.value = value;
    }
}
