package com.angelis.tera.game.domain.entity.xml.creatures.spawn;

import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javolution.util.FastSet;

import com.angelis.tera.common.domain.entity.xml.AbstractXMLEntity;
import com.angelis.tera.game.process.model.enums.SpawnForEventEnum;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "creature_spawn", namespace = "http://angelis.com/creature_spawns")
public class CreatureSpawnsEntity extends AbstractXMLEntity {

    private static final long serialVersionUID = -5082744641054235756L;

    @XmlAttribute(name = "id", required = true)
    private Integer id;
    
    @XmlAttribute(name = "full_id", required = true)
    private int fullId;
    
    @XmlAttribute(name="spawn_for_event", required=false)
    private SpawnForEventEnum spawnForEvent;

    @XmlElement(name = "spawn", namespace = "http://angelis.com/creature_spawns")
    private Set<CreatureSpawnEntity> spawns;

    @Override
    public Integer getId() {
        return id;
    }

    public int getFullId() {
        return fullId;
    }

    public SpawnForEventEnum getSpawnForEvent() {
        return spawnForEvent;
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
