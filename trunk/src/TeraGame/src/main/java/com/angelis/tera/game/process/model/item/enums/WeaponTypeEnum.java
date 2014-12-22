package com.angelis.tera.game.process.model.item.enums;

import com.angelis.tera.game.process.model.player.enums.PlayerClassEnum;

public enum WeaponTypeEnum {
    BOW(PlayerClassEnum.ARCHER),
    LANCE(PlayerClassEnum.LANCER),
    TWIN_SWORD(PlayerClassEnum.WARRIOR),
    GREATSWORD(PlayerClassEnum.SLAYER),
    DISC(PlayerClassEnum.SORCERER),
    AXE(PlayerClassEnum.BERSERKER),
    SCEPTER(PlayerClassEnum.MYSTIC),
    STAFF(PlayerClassEnum.PRIEST),
    WHIP(PlayerClassEnum.REAPER),
    
    ;
    
    public final PlayerClassEnum allowedClass;
    
    WeaponTypeEnum(final PlayerClassEnum allowedClass) {
        this.allowedClass = allowedClass;
    }   
}