package com.angelis.tera.game.xml.entity.items;

import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javolution.util.FastSet;

import org.apache.log4j.Logger;

import com.angelis.tera.common.domain.entity.xml.AbstractXMLEntity;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "item_templates", namespace = "http://angelis.com/item_templates")
public class ItemTemplateEntityHolder extends AbstractXMLEntity {

    private static final long serialVersionUID = -421262067188748519L;

    /** LOGGER */
    private static Logger log = Logger.getLogger(ItemTemplateEntityHolder.class.getName());

    @XmlElement(name = "item_template", namespace = "http://angelis.com/item_templates")
    private Set<ItemTemplateEntity> itemTemplates;

    public Set<ItemTemplateEntity> getItemTemplates() {
        if (itemTemplates == null) {
            itemTemplates = new FastSet<>(0);
        }
        return itemTemplates;
    }

    @Override
    public void onLoad() {
        log.info("Loaded " + getItemTemplates().size() + " item templates !");
    }
}
