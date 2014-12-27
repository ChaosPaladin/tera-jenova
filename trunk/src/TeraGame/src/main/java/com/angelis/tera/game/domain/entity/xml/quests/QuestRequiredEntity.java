package com.angelis.tera.game.domain.entity.xml.quests;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "required", namespace = "http://angelis.com/quests")
public class QuestRequiredEntity {

    @XmlAttribute(name = "quest_id")
    private int questId;

    public int getQuestId() {
        return questId;
    }
}
