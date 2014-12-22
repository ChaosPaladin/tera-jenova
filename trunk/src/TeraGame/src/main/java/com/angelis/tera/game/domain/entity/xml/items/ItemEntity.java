package com.angelis.tera.game.domain.entity.xml.items;

import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javolution.util.FastSet;

import com.angelis.tera.common.domain.entity.xml.AbstractXMLEntity;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "item", namespace = "http://angelis.com/items")
public class ItemEntity extends AbstractXMLEntity {

    private static final long serialVersionUID = 2022193536022689526L;

    @XmlAttribute(name = "item_id")
    private int itemId;
    
    @XmlElement(name="item_action", namespace = "http://angelis.com/items")
    private Set<ItemActionEntity> itemActions;

    public int getItemId() {
        return itemId;
    }

    public Set<ItemActionEntity> getItemActions() {
        if (itemActions == null) {
            itemActions = new FastSet<>(0);
        }
        return itemActions;
    }

    @Override
    public void onLoad() {
    }

}
