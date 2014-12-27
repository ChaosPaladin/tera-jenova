package com.angelis.tera.game.domain.entity.xml.zones;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.angelis.tera.common.domain.entity.xml.AbstractXMLEntity;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="zone", namespace="http://angelis.com/zones")
public class ZoneEntity extends AbstractXMLEntity {

    private static final long serialVersionUID = -1247084685837901281L;

    @XmlAttribute(name="datas")
    private String datas;

    public String getDatas() {
        return datas;
    }
}
