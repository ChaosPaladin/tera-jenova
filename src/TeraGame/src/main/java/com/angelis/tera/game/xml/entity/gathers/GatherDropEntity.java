package com.angelis.tera.game.xml.entity.gathers;

import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javolution.util.FastSet;

import com.angelis.tera.common.domain.entity.xml.AbstractXMLEntity;
import com.angelis.tera.game.xml.entity.DropEntity;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "gather_drop", namespace = "http://angelis.com/gather_drops")
public class GatherDropEntity extends AbstractXMLEntity {

    private static final long serialVersionUID = -1098097106450604745L;

    @XmlAttribute(name = "gather_id")
    private int gatherId;

    @XmlElement(name = "drop", namespace = "http://angelis.com/gather_drops")
    private Set<DropEntity> drops;

    public int getGatherId() {
        return gatherId;
    }

    public Set<DropEntity> getDrops() {
        if (this.drops == null) {
            this.drops = new FastSet<DropEntity>(0);
        }
        return drops;
    }

    @Override
    public void onLoad() {
    }
}
