package com.angelis.tera.game.domain.entity.xml.mounts;

import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javolution.util.FastSet;

import org.apache.log4j.Logger;

import com.angelis.tera.common.domain.entity.xml.AbstractXMLEntity;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "mounts", namespace = "http://angelis.com/mounts")
public class MountEntityHolder extends AbstractXMLEntity {

    private static final long serialVersionUID = 4630059910453310920L;

    /** LOGGER */
    private static Logger log = Logger.getLogger(MountEntityHolder.class.getName());
    
    @XmlElement(name = "mount", namespace = "http://angelis.com/mounts")
    private Set<MountEntity> mounts;

    public Set<MountEntity> getMounts() {
        if (mounts == null) {
            mounts = new FastSet<>(0);
        }
        return mounts;
    }

    @Override
    public void onLoad() {
        log.info("Loaded " + getMounts().size() + " mounts !");
    }
}
