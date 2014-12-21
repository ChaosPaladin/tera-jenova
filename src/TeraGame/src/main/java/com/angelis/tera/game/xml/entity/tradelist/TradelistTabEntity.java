package com.angelis.tera.game.xml.entity.tradelist;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.angelis.tera.game.process.model.tradelist.enums.TradelistTabNameEnum;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "tab", namespace = "http://angelis.com/tradelists")
public class TradelistTabEntity {
    @XmlAttribute(name = "tradelist_tab_name")
    private TradelistTabNameEnum tradelistTabName;

    @XmlElement(name = "item", namespace = "http://angelis.com/tradelists")
    private List<TradelistTabItemEntity> items;

    public TradelistTabNameEnum getTradelistTabName() {
        return tradelistTabName;
    }

    public List<TradelistTabItemEntity> getItems() {
        return items;
    }
}
