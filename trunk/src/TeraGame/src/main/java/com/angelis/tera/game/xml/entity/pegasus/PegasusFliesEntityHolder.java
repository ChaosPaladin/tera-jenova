package com.angelis.tera.game.xml.entity.pegasus;

import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javolution.util.FastSet;

import org.apache.log4j.Logger;

import com.angelis.tera.common.domain.entity.xml.AbstractXMLEntity;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "pegasus_flies", namespace = "http://angelis.com/pegasus_flies")
public class PegasusFliesEntityHolder extends AbstractXMLEntity {

    private static final long serialVersionUID = -611520429752120262L;

    /** LOGGER */
    private static Logger log = Logger.getLogger(PegasusFliesEntityHolder.class.getName());

    @XmlElement(name = "pegasus_fly", namespace = "http://angelis.com/pegasus_flies")
    private Set<PegasusFlyEntity> pegasusFlies;
    
    public Set<PegasusFlyEntity> getPegasusFlies() {
        if (pegasusFlies == null) {
            pegasusFlies = new FastSet<>(0);
        }
        return pegasusFlies;
    }

    @Override
    public void onLoad() {
        log.info("Loaded " + getPegasusFlies().size() + " pegasus flies !");
    }
    
}
