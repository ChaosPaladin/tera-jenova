package com.angelis.tera.game.domain.entity.xml.players;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "player_experience")
public class PlayerExperienceEntity {

    @XmlAttribute(name = "level")
    private int level;

    @XmlAttribute(name = "experience")
    private long experience;

    public int getLevel() {
        return level;
    }

    public long getExperience() {
        return experience;
    }
}
