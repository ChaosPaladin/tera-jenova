package com.angelis.tera.game.domain.entity.xml.items;

import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javolution.util.FastSet;

import org.apache.log4j.Logger;

import com.angelis.tera.common.domain.entity.xml.AbstractXMLEntity;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "items", namespace = "http://angelis.com/items")
public class ItemEntityHolder extends AbstractXMLEntity {

    private static final long serialVersionUID = -421262067188748519L;

    /** LOGGER */
    private static Logger log = Logger.getLogger(ItemEntityHolder.class.getName());

    @XmlElement(name = "item", namespace = "http://angelis.com/items")
    private Set<ItemEntity> items;

    public Set<ItemEntity> getItems() {
        if (items == null) {
            items = new FastSet<>(0);
        }
        return items;
    }

    @Override
    public void onLoad() {
        log.info("Loaded " + getItems().size() + " items !");
    }
}
