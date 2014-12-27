package com.angelis.tera.game.domain.entity.xml.players;

import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javolution.util.FastSet;

import com.angelis.tera.game.process.model.player.enums.PlayerClassEnum;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "player_itemset", namespace="http://angelis.com/player_itemsets")
public class PlayerItemSetEntity {
    
    @XmlAttribute
    private PlayerClassEnum targetClass;
    
    @XmlElement(name="player_item", namespace="http://angelis.com/player_itemsets")
    private Set<PlayerItemEntity> items;

    public PlayerClassEnum getTargetClass() {
        return targetClass;
    }

    public Set<PlayerItemEntity> getItems() {
        if (items == null) {
            items = new FastSet<>(0);
        }
        return items;
    }
}
