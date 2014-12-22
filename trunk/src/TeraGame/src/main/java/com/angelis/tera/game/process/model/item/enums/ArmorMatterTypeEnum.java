package com.angelis.tera.game.process.model.item.enums;

import com.angelis.tera.game.process.model.player.enums.PlayerClassEnum;

public enum ArmorMatterTypeEnum {
    MAIL(PlayerClassEnum.BERSERKER, PlayerClassEnum.LANCER),
    LEATHER(PlayerClassEnum.ARCHER, PlayerClassEnum.SLAYER, PlayerClassEnum.WARRIOR, PlayerClassEnum.REAPER),
    CLOTH(PlayerClassEnum.MYSTIC, PlayerClassEnum.PRIEST, PlayerClassEnum.SORCERER),
    ;
    
    public final PlayerClassEnum[] allowedClasses;
    
    ArmorMatterTypeEnum(final PlayerClassEnum... allowedClasses) {
        this.allowedClasses = allowedClasses;
    }
}