package com.angelis.tera.game.xml.entity.players;

import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javolution.util.FastSet;

import org.apache.log4j.Logger;

import com.angelis.tera.common.domain.entity.xml.AbstractXMLEntity;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="player_experiences", namespace="http://angelis.com/player_experiences")
public class PlayerExperienceEntityHolder extends AbstractXMLEntity {

    private static final long serialVersionUID = 6037173453270745675L;
    
    /** LOGGER */
    private static Logger log = Logger.getLogger(PlayerExperienceEntityHolder.class.getName());
    
    @XmlElement(name="player_experience", namespace="http://angelis.com/player_experiences")
    private Set<PlayerExperienceEntity> experiences;

    public Set<PlayerExperienceEntity> getExperiences() {
        if (this.experiences == null) {
            this.experiences = new FastSet<PlayerExperienceEntity>(0);
        }
        return this.experiences;
    }

    @Override
    public void onLoad() {
        log.info("Loaded "+getExperiences().size()+" experiences !");
    }
}
