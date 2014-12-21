package com.angelis.tera.game.xml.entity.gathers;

import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javolution.util.FastSet;

import org.apache.log4j.Logger;

import com.angelis.tera.common.domain.entity.xml.AbstractXMLEntity;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="gather_templates", namespace="http://angelis.com/gather_templates")
public class GatherTemplateEntityHolder extends AbstractXMLEntity {

    private static final long serialVersionUID = -8438477223001701584L;
    
    /** LOGGER */
    private static Logger log = Logger.getLogger(GatherTemplateEntityHolder.class.getName());
    
    @XmlElement(name="gather_template", namespace="http://angelis.com/gather_templates")
    private Set<GatherTemplateEntity> gatherTemplates;
    
    public Set<GatherTemplateEntity> getGatherTemplates() {
        if (gatherTemplates == null) {
            gatherTemplates = new FastSet<>(0);
        }
        return this.gatherTemplates;
    }

    @Override
    public void onLoad() {
        log.info("Loaded "+getGatherTemplates().size()+" gather templates !");
    }
}
