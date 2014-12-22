package com.angelis.tera.game.process.model.storage.enums;

public enum InventorySizeEnum {
    FIRST(40, 0),
    SECOND(48, 0),
    THIRD(56, 1500),
    FOURTH(64, 30000),
    FIFTH(72, 800000),
    SIXTH(80, 1000000),
    SEVENTH(88, -1),
    HEIGHT(96, -1),
    NINE(104, -1)
    ;
    
    public final int value;
    public final int cost;
    
    InventorySizeEnum(final int value, final int cost) {
        this.value = value;
        this.cost = cost;
    }

    public static InventorySizeEnum fromValue(final int value) {
        for (final InventorySizeEnum inventorySize : InventorySizeEnum.values()) {
            if (inventorySize.value == value) {
                return inventorySize;
            }
        }
        return null;
    }
}
