package com.angelis.tera.game.xml.entity.quests;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "step_value", namespace = "http://angelis.com/quests")
public class QuestStepValueEntity {
    @XmlAttribute(name = "object_id")
    private int objectId;
    
    @XmlAttribute(name = "amount")
    private int amount;

    public int getObjectId() {
        return objectId;
    }

    public int getAmount() {
        return amount;
    }
}
