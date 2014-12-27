package com.angelis.tera.game.process.model.storage.enums;

public enum InventorySlotEnum {
    WEAPON(1),
    
    ARMOR(3),
    GLOVES(4),
    FOOT(5),
    EARING_LEFT(6),
    EARING_RIGHT(7),
    RING_LEFT(8),
    RING_RIGHT(9),
    NECK(10),
    UNDERWEAR(11),
    HAIR(12),
    FACE(13),
    HAIR_DECORATION(14),
    MASK(15),
    WEAPON_APPEARANCE(16),
    ARMOR_APPEARANCE(17),
    BACK(18),
    ;

    public final int value;
    
    InventorySlotEnum(final int value) {
        this.value = value;
    }
}
