package com.angelis.tera.game.domain.entity.xml.gathers;

import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javolution.util.FastSet;

import com.angelis.tera.common.domain.entity.xml.AbstractXMLEntity;
import com.angelis.tera.game.domain.entity.xml.SpawnEntity;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "gather_spawn", namespace = "http://angelis.com/gather_spawns")
public class GatherSpawnEntity extends AbstractXMLEntity {
    
    private static final long serialVersionUID = -2314851677719238250L;

    @XmlAttribute(name = "id")
    private Integer id;
    
    @XmlElement(name="spawn", namespace = "http://angelis.com/gather_spawns")
    private Set<SpawnEntity> spawns;

    public Integer getId() {
        return id;
    }

    public Set<SpawnEntity> getSpawns() {
        if (spawns == null) {
            spawns = new FastSet<>(0);
        }
        return spawns;
    }

    @Override
    public void onLoad() {
    }
}
