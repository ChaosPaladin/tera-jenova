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
@XmlRootElement(name="player_class_skills", namespace="http://angelis.com/player_class_skills")
public class PlayerClassSkillsEntityHolder extends AbstractXMLEntity {

    private static final long serialVersionUID = -4060353715031966947L;

    /** LOGGER */
    private static Logger log = Logger.getLogger(PlayerClassSkillsEntityHolder.class.getName());

    @XmlElement(name = "player_class_skill", namespace = "http://angelis.com/player_class_skills")
    private Set<PlayerClassSkillsEntity> playerClassSkills;
    
    public Set<PlayerClassSkillsEntity> getPlayerClassSkills() {
        if (playerClassSkills == null) {
            playerClassSkills = new FastSet<>(0);
        }
        return playerClassSkills;
    }

    @Override
    public void onLoad() {
        log.info("Loaded " + getPlayerClassSkills().size() + " player class skills !");
    }
}
