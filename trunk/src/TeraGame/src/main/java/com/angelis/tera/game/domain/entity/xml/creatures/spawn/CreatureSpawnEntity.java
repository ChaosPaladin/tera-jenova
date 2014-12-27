package com.angelis.tera.game.domain.entity.xml.creatures.spawn;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.angelis.tera.game.domain.entity.xml.SpawnEntity;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="spawn", namespace = "http://angelis.com/creature_spawns")
public class CreatureSpawnEntity extends SpawnEntity {
    
    @XmlAttribute(name = "heading")
    private short heading;

    public short getHeading() {
        return heading;
    }
}
