package com.angelis.tera.game.xml.entity.tradelist;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "tradelist", namespace = "http://angelis.com/tradelists")
public class TradelistEntity {
    
    @XmlAttribute(name = "full_id")
    private int creatureFullId;
    
    @XmlElement(name = "tab", namespace = "http://angelis.com/tradelists")
    private List<TradelistTabEntity> tabs;

    public int getCreatureFullId() {
        return creatureFullId;
    }

    public List<TradelistTabEntity> getTabs() {
        return tabs;
    }
}
