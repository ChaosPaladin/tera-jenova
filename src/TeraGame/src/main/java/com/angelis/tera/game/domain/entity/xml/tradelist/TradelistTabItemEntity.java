package com.angelis.tera.game.domain.entity.xml.tradelist;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "item", namespace = "http://angelis.com/tradelists")
public class TradelistTabItemEntity {
    @XmlAttribute(name = "id")
    private int id;

    public int getId() {
        return id;
    }
}
