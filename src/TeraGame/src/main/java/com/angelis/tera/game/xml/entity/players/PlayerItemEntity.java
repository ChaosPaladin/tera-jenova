package com.angelis.tera.game.xml.entity.players;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.angelis.tera.game.process.model.storage.enums.InventorySlotEnum;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "player_item", namespace = "http://angelis.com/player_itemsets")
public class PlayerItemEntity {

    @XmlAttribute(name = "itemId")
    private int itemId;

    @XmlAttribute(name = "slot")
    private InventorySlotEnum inventorySlot;

    public int getItemId() {
        return itemId;
    }

    public InventorySlotEnum getSlot() {
        return inventorySlot;
    }
}
