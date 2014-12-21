package com.angelis.tera.game.xml.entity.creatures.drop;

import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javolution.util.FastSet;

import org.apache.log4j.Logger;

import com.angelis.tera.common.domain.entity.xml.AbstractXMLEntity;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="creature_drops", namespace="http://angelis.com/creature_drops")
public class CreatureDropEntityHolder extends AbstractXMLEntity {

    private static final long serialVersionUID = -1081662686213542284L;

    /** LOGGER */
    private static Logger log = Logger.getLogger(CreatureDropEntityHolder.class.getName());
    
    @XmlElement(name="creature_drop", namespace = "http://angelis.com/creature_drops")
    private Set<CreatureDropEntity> creatureDrops;

    public Set<CreatureDropEntity> getCreatureDrops() {
        if (this.creatureDrops == null) {
            this.creatureDrops = new FastSet<CreatureDropEntity>(0);
        }
        return this.creatureDrops;
    }

    @Override
    public void onLoad() {
        log.info("Loaded "+getCreatureDrops().size()+" creature drops !");
    }
}
