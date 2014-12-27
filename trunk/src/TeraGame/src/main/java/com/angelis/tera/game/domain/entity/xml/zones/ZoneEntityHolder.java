package com.angelis.tera.game.domain.entity.xml.zones;

import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javolution.util.FastSet;

import org.apache.log4j.Logger;

import com.angelis.tera.common.domain.entity.xml.AbstractXMLEntity;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "zones", namespace = "http://angelis.com/zones")
public class ZoneEntityHolder extends AbstractXMLEntity {

    private static final long serialVersionUID = 17300767835114877L;

    /** LOGGER */
    private static Logger log = Logger.getLogger(ZoneEntityHolder.class.getName());

    @XmlElement(name = "zone", namespace = "http://angelis.com/zones")
    private Set<ZoneEntity> zones;

    public Set<ZoneEntity> getZones() {
        if (zones == null) {
            zones = new FastSet<>(0);
        }
        return zones;
    }

    @Override
    public void onLoad() {
        log.info("Loaded " + getZones().size() + " zones !");
    }
}
