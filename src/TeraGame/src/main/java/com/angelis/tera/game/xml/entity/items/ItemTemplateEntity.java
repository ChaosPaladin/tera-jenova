package com.angelis.tera.game.xml.entity.items;

import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javolution.util.FastSet;

import com.angelis.tera.common.domain.entity.xml.AbstractXMLEntity;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "item_template", namespace = "http://angelis.com/item_templates")
public class ItemTemplateEntity extends AbstractXMLEntity {

    private static final long serialVersionUID = 2022193536022689526L;

    @XmlAttribute(name = "item_id")
    private int itemId;
    
    @XmlElement(name="item_action", namespace = "http://angelis.com/item_templates")
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
