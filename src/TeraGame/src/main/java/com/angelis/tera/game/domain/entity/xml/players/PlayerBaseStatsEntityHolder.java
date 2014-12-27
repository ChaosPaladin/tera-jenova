package com.angelis.tera.game.domain.entity.xml.players;

import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javolution.util.FastSet;

import org.apache.log4j.Logger;

import com.angelis.tera.common.domain.entity.xml.AbstractXMLEntity;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "player_basestats", namespace = "http://angelis.com/player_basestats")
public class PlayerBaseStatsEntityHolder extends AbstractXMLEntity {

    private static final long serialVersionUID = 2941004152168232255L;

    /** LOGGER */
    private static Logger log = Logger.getLogger(PlayerBaseStatsEntityHolder.class.getName());

    @XmlElement(name = "player_basestat", namespace = "http://angelis.com/player_basestats")
    private Set<PlayerBaseStatsEntity> playerBaseStats;

    public Set<PlayerBaseStatsEntity> getPlayerBaseStats() {
        if (playerBaseStats == null) {
            playerBaseStats = new FastSet<>(0);
        }
        return playerBaseStats;
    }

    @Override
    public void onLoad() {
        log.info("Loaded " + getPlayerBaseStats().size() + " base stats !");
    }
}
