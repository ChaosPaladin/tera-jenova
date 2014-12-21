package com.angelis.tera.game.domain.entity.xml.quests;

import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javolution.util.FastSet;

import org.apache.log4j.Logger;

import com.angelis.tera.common.domain.entity.xml.AbstractXMLEntity;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "quests", namespace = "http://angelis.com/quests")
public class QuestEntityHolder extends AbstractXMLEntity {

    private static final long serialVersionUID = 8666345691399104031L;

    /** LOGGER */
    private static Logger log = Logger.getLogger(QuestEntityHolder.class.getName());

    @XmlElement(name = "quest", namespace = "http://angelis.com/quests")
    private Set<QuestEntity> quests;

    public Set<QuestEntity> getQuests() {
        if (quests == null) {
            quests = new FastSet<QuestEntity>(0);
        }
        return quests;
    }

    @Override
    public void onLoad() {
        log.info("Loaded " + getQuests().size() + " quests !");
    }
}
