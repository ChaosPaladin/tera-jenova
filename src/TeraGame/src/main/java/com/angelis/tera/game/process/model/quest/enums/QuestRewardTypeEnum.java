package com.angelis.tera.game.process.model.quest.enums;

public enum QuestRewardTypeEnum {
    SELECTABLE(1),
    UNSPECIFIED(2),
    ALL(3)
    ;
    
    public final int value;
    
    QuestRewardTypeEnum(final int value) {
        this.value = value;
    }
}
