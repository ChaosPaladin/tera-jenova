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
@XmlRootElement(name="player_race_skills", namespace="http://angelis.com/player_race_skills")
public class PlayerRaceSkillsEntityHolder extends AbstractXMLEntity {

    private static final long serialVersionUID = -4060353715031966947L;

    /** LOGGER */
    private static Logger log = Logger.getLogger(PlayerRaceSkillsEntityHolder.class.getName());

    @XmlElement(name = "player_race_skill", namespace = "http://angelis.com/player_race_skills")
    private Set<PlayerRaceSkillsEntity> playerRaceSkills;
    
    public Set<PlayerRaceSkillsEntity> getPlayerRaceSkills() {
        if (playerRaceSkills == null) {
            playerRaceSkills = new FastSet<>(0);
        }
        return playerRaceSkills;
    }

    @Override
    public void onLoad() {
        log.info("Loaded " + getPlayerRaceSkills().size() + " player race skills !");
    }

}
