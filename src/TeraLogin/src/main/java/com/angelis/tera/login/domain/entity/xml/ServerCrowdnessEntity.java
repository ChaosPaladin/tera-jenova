package com.angelis.tera.login.domain.entity.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "category")
public class ServerCrowdnessEntity {
    @XmlAttribute(name = "sort")
    private int sort;
    
    @XmlValue()
    private String value;

    public int getSort() {
        return sort;
    }

    public void setSort(final int sort) {
        this.sort = sort;
    }

    public String getValue() {
        return value;
    }

    public void setValue(final String value) {
        this.value = value;
    }
}
