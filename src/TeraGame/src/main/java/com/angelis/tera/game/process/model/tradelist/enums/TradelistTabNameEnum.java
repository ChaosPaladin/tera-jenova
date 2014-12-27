package com.angelis.tera.game.process.model.tradelist.enums;

public enum TradelistTabNameEnum {
    
    TROMPERY_ENSEMBLE(7430),

    GLYPHE(2001),

    MELEE_WEAPON(2111),
    DISTANT_WEAPON(2112),

    //PAINT MERCHANT
    PAINT_BOTTLE(2401),
    PAINT_NECESSITY(2402),

    //SPECIALITY STORE
    BATTLE_FURNITURE(2501),
    ARCANIQUE_FURNITURE(2502),    
    WEAPON_CRYSTAL(2503),
    ARMOR_CRYSTAL(2504),

    WEAPON(2601),
    METAL_ARMOR(2602),
    LEATHER_ARMOR(2603),
    CLOTH_ARMOR(2604),
    ARMOR(1542),
    
    COSTUME(2801),
    
    //GENERAL MERCHANT
    GENERAL_GOODS(1601),
    BATTLE(1602),
    EXTRACTION_LESSON(1603),
    SCROLL(1604),
    
    //PET MERCHANT
    PET(1301),
    PET_TRAINER(1302),
    
    //PVP MERCHANT
    BLOODRAVESET(1421),
    STRIKEFORCEPLANS(1422),
    NIGHTFORGEDESIGN(1423),
    PROVISIONING(1424),
    EQUIPEMENT(1411),
    PLANS(1413),
    SACRIFICIELEQUIPEMENT(1412),
    
    TEST(91251),
    TEST2(91252);
    
    public final int value;
    
    TradelistTabNameEnum(final int value) {
        this.value = value;
    }
}
