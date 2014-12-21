package com.angelis.tera.game.xml.entity.players;

import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javolution.util.FastSet;

import org.apache.log4j.Logger;

import com.angelis.tera.common.domain.entity.xml.AbstractXMLEntity;
import com.angelis.tera.game.process.model.player.enums.PlayerClassEnum;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="player_itemsets", namespace="http://angelis.com/player_itemsets")
public class PlayerItemSetEntityHolder extends AbstractXMLEntity {

    private static final long serialVersionUID = 348233423217946855L;

    /** LOGGER */
    private static Logger log = Logger.getLogger(PlayerItemSetEntityHolder.class.getName());

    @XmlElement(name="player_itemset", namespace="http://angelis.com/player_itemsets")
    private Set<PlayerItemSetEntity> playerItemsets;
    
    public Set<PlayerItemSetEntity> getPlayerItemsets() {
        if (playerItemsets == null) {
            playerItemsets = new FastSet<>(0);
        }
        return playerItemsets;
    }
    
    public PlayerItemSetEntity getPlayerItemsetsByTargetClass(final PlayerClassEnum classEnum) {
        for (final PlayerItemSetEntity itemSet : this.getPlayerItemsets()) {
            if (itemSet.getTargetClass() == classEnum) {
                return itemSet;
            }
        }
        return null;
    }

    @Override
    public void onLoad() {
        log.info("Loaded "+getPlayerItemsets().size()+" player items sets !");
    }
}
