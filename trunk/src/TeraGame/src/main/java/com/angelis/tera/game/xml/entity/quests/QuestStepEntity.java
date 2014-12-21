package com.angelis.tera.game.xml.entity.quests;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.angelis.tera.game.process.model.quest.enums.QuestStepTypeEnum;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "step", namespace = "http://angelis.com/quests")
public class QuestStepEntity {

    @XmlAttribute(name = "type")
    private QuestStepTypeEnum questStepType;

    @XmlElement(name = "step_value", namespace = "http://angelis.com/quests")
    private List<QuestStepValueEntity> stepValues;;

    public QuestStepTypeEnum getQuestStepType() {
        return questStepType;
    }

    public List<QuestStepValueEntity> getStepValues() {
        return stepValues;
    }
}
