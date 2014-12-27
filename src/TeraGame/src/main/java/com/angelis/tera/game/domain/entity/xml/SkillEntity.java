package com.angelis.tera.game.domain.entity.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "skill", namespace = "http://angelis.com/base")
public class SkillEntity {

    @XmlAttribute(name = "id")
    private int id;

    @XmlAttribute(name = "required_level")
    private int requiredLevel;

    public int getId() {
        return id;
    }

    public int getRequiredLevel() {
        return requiredLevel;
    }
}
