package com.angelis.tera.game.domain.entity.xml.quests;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "reward", namespace = "http://angelis.com/quests")
public class QuestRewardEntity {
    @XmlAttribute(name = "item_id")
    private int itemId;
    
    @XmlAttribute(name="amount")
    private int amount;

    public int getItemId() {
        return itemId;
    }

    public int getAmount() {
        return amount;
    }
}
