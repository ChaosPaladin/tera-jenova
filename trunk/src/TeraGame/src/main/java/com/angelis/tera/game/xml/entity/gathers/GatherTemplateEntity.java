package com.angelis.tera.game.xml.entity.gathers;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.angelis.tera.common.domain.entity.xml.AbstractXMLEntity;
import com.angelis.tera.game.process.model.player.gather.enums.GatherTypeEnum;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "gather_template", namespace = "http://angelis.com/gather_templates")
public class GatherTemplateEntity extends AbstractXMLEntity {

    private static final long serialVersionUID = 7573819030626833591L;

    @XmlAttribute(name = "id")
    private Integer id;
    
    @XmlAttribute(name = "gather_type")
    private GatherTypeEnum gatherType;
    
    @XmlAttribute(name="min_level")
    private int minLevel;
    
    @XmlAttribute(name="max_level")
    private int maxLevel;
    
    @XmlAttribute(name="gather_cost_point")
    private int gatherCostPoint;

    @Override
    public Integer getId() {
        return id;
    }

    public GatherTypeEnum getGatherType() {
        return gatherType;
    }

    public int getMinLevel() {
        return minLevel;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public int getGatherCostPoint() {
        return gatherCostPoint;
    }
}
