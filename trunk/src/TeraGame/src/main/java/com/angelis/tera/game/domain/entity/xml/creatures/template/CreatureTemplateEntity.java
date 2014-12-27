package com.angelis.tera.game.domain.entity.xml.creatures.template;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.angelis.tera.common.domain.entity.xml.AbstractXMLEntity;
import com.angelis.tera.game.domain.entity.xml.BaseStatsEntity;
import com.angelis.tera.game.process.model.creature.enums.RankEnum;
import com.angelis.tera.game.process.model.enums.CreatureTitleEnum;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="creature_template", namespace="http://angelis.com/creature_templates")
public class CreatureTemplateEntity extends AbstractXMLEntity {

    private static final long serialVersionUID = -3799335164347861818L;

    @XmlAttribute(name = "full_id")
    private int fullId;

    @XmlAttribute(name = "rank")
    private RankEnum rank;
    
    @XmlAttribute(name = "exp")
    private int exp;
    
    @XmlAttribute(name = "hunting_zone_id")
    private int huntingZoneId;
    
    @XmlAttribute(name="model_id")
    private int modelId;
    
    @XmlAttribute(name = "offensive")
    private boolean offensive;
    
    @XmlAttribute(name = "title")
    private CreatureTitleEnum creatureTitle;
    
    @XmlElement(name = "basestats", namespace = "http://angelis.com/creature_templates")
    private BaseStatsEntity baseStats;

    public int getFullId() {
        return fullId;
    }

    public RankEnum getRank() {
        return rank;
    }

    public int getExp() {
        return exp;
    }

    public int getHuntingZoneId() {
        return huntingZoneId;
    }

    public int getModelId() {
        return modelId;
    }

    public boolean isOffensive() {
        return offensive;
    }

    public CreatureTitleEnum getCreatureTitle() {
        return creatureTitle;
    }

    public BaseStatsEntity getBaseStats() {
        return baseStats;
    }
}
