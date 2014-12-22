package com.angelis.tera.game.domain.entity.xml.pegasus;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "fly", namespace = "http://angelis.com/pegasus_flies")
public class FlyEntity {
    @XmlAttribute(name = "id")
    private int id;

    @XmlAttribute(name = "cost")
    private int cost;

    @XmlAttribute(name = "level")
    private int level;

    @XmlAttribute(name = "to_name_id")
    private int toNameId;

    public int getId() {
        return id;
    }

    public int getCost() {
        return cost;
    }

    public int getLevel() {
        return level;
    }

    public int getToNameId() {
        return toNameId;
    }
}
