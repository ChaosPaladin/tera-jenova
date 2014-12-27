package com.angelis.tera.game.domain.entity.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="spawn", namespace = "http://angelis.com/base")
public class SpawnEntity {

    @XmlAttribute(name="mapId")
    private int mapId;
    
    @XmlAttribute(name="x")
    private float x;
    
    @XmlAttribute(name="y")
    private float y;
    
    @XmlAttribute(name="z")
    private float z;
    
    public int getMapId() {
        return mapId;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }
}
