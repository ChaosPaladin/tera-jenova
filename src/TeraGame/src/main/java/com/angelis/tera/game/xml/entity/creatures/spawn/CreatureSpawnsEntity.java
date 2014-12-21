package com.angelis.tera.game.xml.entity.creatures.spawn;

import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javolution.util.FastSet;

import com.angelis.tera.common.domain.entity.xml.AbstractXMLEntity;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "creature_spawn", namespace = "http://angelis.com/creature_spawns")
public class CreatureSpawnsEntity extends AbstractXMLEntity {

    private static final long serialVersionUID = -5082744641054235756L;

    @XmlAttribute(name = "id", required = true)
    private Integer id;
    
    @XmlAttribute(name = "full_id", required = true)
    private int fullId;

    @XmlElement(name = "spawn", namespace = "http://angelis.com/creature_spawns")
    private Set<CreatureSpawnEntity> spawns;

    @Override
    public Integer getId() {
        return id;
    }

    public int getFullId() {
        return fullId;
    }

    public Set<CreatureSpawnEntity> getSpawns() {
        if (this.spawns == null) {
            this.spawns = new FastSet<>(0);
        }
        return this.spawns;
    }

    @Override
    public void onLoad() {
    }
}
