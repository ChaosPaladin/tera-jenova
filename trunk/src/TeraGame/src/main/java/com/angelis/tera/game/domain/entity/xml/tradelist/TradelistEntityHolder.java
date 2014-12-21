package com.angelis.tera.game.domain.entity.xml.tradelist;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javolution.util.FastList;

import org.apache.log4j.Logger;

import com.angelis.tera.common.domain.entity.xml.AbstractXMLEntity;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "tradelists", namespace = "http://angelis.com/tradelists")
public class TradelistEntityHolder extends AbstractXMLEntity {

    private static final long serialVersionUID = -4867486457270293967L;
    
    /** LOGGER */
    private static Logger log = Logger.getLogger(TradelistEntityHolder.class.getName());
    
    @XmlElement(name = "tradelist", namespace = "http://angelis.com/tradelists")
    private List<TradelistEntity> tradelists;

    public List<TradelistEntity> getTradelists() {
        if (tradelists == null) {
            tradelists = new FastList<>(0);
        }
        return this.tradelists;
    }

    @Override
    public void onLoad() {
        log.info("Loaded " + getTradelists().size() + " tradelists !");
    }
}
