package com.angelis.tera.game.xml.entity.players;

import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.angelis.tera.common.domain.entity.xml.AbstractXMLEntity;
import com.angelis.tera.game.process.model.player.enums.PlayerClassEnum;
import com.angelis.tera.game.xml.entity.BaseStatsEntity;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "player_basestat", namespace = "http://angelis.com/player_basestats")
public class PlayerBaseStatsEntity extends AbstractXMLEntity {

    private static final long serialVersionUID = 2410250175955924178L;

    @XmlAttribute(name="targetClass")
    private PlayerClassEnum targetClass;

    @XmlElement(name = "basestats", namespace = "http://angelis.com/player_basestats")
    private Set<BaseStatsEntity> baseStats;
    

    public PlayerClassEnum getTargetClass() {
        return targetClass;
    }

    public Set<BaseStatsEntity> getBaseStats() {
        return baseStats;
    }

    @Override
    public void onLoad() {
    }
}
