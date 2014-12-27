package com.angelis.tera.game.config;

import com.angelis.tera.common.config.Property;

public class QuestConfig {
    @Property(key = "quest.experience.rate", defaultValue = "1")
    public static int QUEST_EXPERIENCE_RATE;
    
    @Property(key = "quest.money.rate", defaultValue = "1")
    public static int QUEST_MONEY_RATE;
}
