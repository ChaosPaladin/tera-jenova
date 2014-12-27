package com.angelis.tera.game.domain.entity.xml.pegasus;

import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "pegasus_fly", namespace = "http://angelis.com/pegasus_flies")
public class PegasusFlyEntity {

    @XmlAttribute(name = "full_id")
    private int creatureFullId;

    @XmlAttribute(name = "from_name_id")
    private int fromNameId;

    @XmlElement(name = "fly", namespace = "http://angelis.com/pegasus_flies")
    private Set<FlyEntity> pegasusFlies;

    public int getCreatureFullId() {
        return creatureFullId;
    }

    public int getFromNameId() {
        return fromNameId;
    }

    public Set<FlyEntity> getPegasusFlies() {
        return pegasusFlies;
    }
}
