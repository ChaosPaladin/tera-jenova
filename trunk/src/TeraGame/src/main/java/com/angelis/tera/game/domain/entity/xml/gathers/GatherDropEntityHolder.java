package com.angelis.tera.game.domain.entity.xml.gathers;

import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javolution.util.FastSet;

import org.apache.log4j.Logger;

import com.angelis.tera.common.domain.entity.xml.AbstractXMLEntity;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="gather_drops", namespace="http://angelis.com/gather_drops")
public class GatherDropEntityHolder extends AbstractXMLEntity {

    private static final long serialVersionUID = -353829667544617610L;

    /** LOGGER */
    private static Logger log = Logger.getLogger(GatherDropEntityHolder.class.getName());
    
    @XmlElement(name="gather_drop", namespace="http://angelis.com/gather_drops")
    private Set<GatherDropEntity> gatherDrops;
    
    public Set<GatherDropEntity> getGatherDrops() {
        if (gatherDrops == null) {
            gatherDrops = new FastSet<>(0);
        }
        return gatherDrops;
    }

    @Override
    public void onLoad() {
        log.info("Loaded "+getGatherDrops().size()+" gather drops !");
    }
}
