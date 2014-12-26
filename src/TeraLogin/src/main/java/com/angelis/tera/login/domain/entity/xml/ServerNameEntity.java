package com.angelis.tera.login.domain.entity.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "name")
public class ServerNameEntity {
    @XmlAttribute(name = "raw_name")
    private String rawName;

    @XmlValue()
    private String value;

    public String getRawName() {
        return rawName;
    }

    public void setRawName(final String rawName) {
        this.rawName = rawName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(final String value) {
        this.value = value;
    }
}
