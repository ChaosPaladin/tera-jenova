package com.angelis.tera.game.domain.entity.xml.creatures.template;

import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javolution.util.FastSet;

import org.apache.log4j.Logger;

import com.angelis.tera.common.domain.entity.xml.AbstractXMLEntity;
import com.angelis.tera.game.domain.entity.xml.creatures.drop.CreatureDropEntityHolder;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="creature_templates", namespace="http://angelis.com/creature_templates")
public class CreatureTemplateEntityHolder extends AbstractXMLEntity {

    private static final long serialVersionUID = 1262475264628559730L;
    
    /** LOGGER */
    private static Logger log = Logger.getLogger(CreatureDropEntityHolder.class.getName());
    
    @XmlElement(name="creature_template", namespace = "http://angelis.com/creature_templates")
    private Set<CreatureTemplateEntity> creatureTemplates;

    public Set<CreatureTemplateEntity> getCreatureTemplates() {
        if (creatureTemplates == null) {
            creatureTemplates = new FastSet<>(0);
        }
        return creatureTemplates;
    }

    @Override
    public void onLoad() {
        log.info("Loaded "+getCreatureTemplates().size()+" creature templates !");
    }
}
