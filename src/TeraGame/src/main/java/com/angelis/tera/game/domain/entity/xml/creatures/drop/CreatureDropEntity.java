package com.angelis.tera.game.domain.entity.xml.creatures.drop;

import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javolution.util.FastSet;

import com.angelis.tera.common.domain.entity.xml.AbstractXMLEntity;
import com.angelis.tera.game.domain.entity.xml.DropEntity;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "creature_drop", namespace = "http://angelis.com/creature_drops")
public class CreatureDropEntity extends AbstractXMLEntity {

    private static final long serialVersionUID = 3991450977758936639L;

    @XmlAttribute(name = "full_id")
    private Integer creatureFullId;

    @XmlAttribute(name = "name")
    private String name;

    @XmlElement(name = "drop", namespace = "http://angelis.com/creature_drops")
    private Set<DropEntity> drops;

    public Integer getCreatureFullId() {
        return creatureFullId;
    }

    public String getName() {
        return name;
    }

    public Set<DropEntity> getDrops() {
        if (this.drops == null) {
            this.drops = new FastSet<DropEntity>(0);
        }
        return this.drops;
    }

    @Override
    public void onLoad() {
    }
}
