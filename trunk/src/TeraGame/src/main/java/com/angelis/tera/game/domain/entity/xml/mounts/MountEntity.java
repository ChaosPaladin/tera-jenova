package com.angelis.tera.game.domain.entity.xml.mounts;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "mount", namespace = "http://angelis.com/mounts")
public class MountEntity {

    @XmlAttribute(name = "id")
    private int id;

    @XmlAttribute(name = "skill_id")
    private int skillId;

    @XmlAttribute(name = "speed")
    private int speed;

    public int getId() {
        return id;
    }

    public int getSkillId() {
        return skillId;
    }

    public int getSpeed() {
        return speed;
    }
}
