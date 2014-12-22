package com.angelis.tera.game.domain.entity.xml.items;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.angelis.tera.common.domain.entity.xml.AbstractXMLEntity;
import com.angelis.tera.game.process.model.item.enums.ItemActionTypeEnum;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "item_action", namespace = "http://angelis.com/item_templates")
public class ItemActionEntity extends AbstractXMLEntity {

    @XmlAttribute(name = "action_type")
    private ItemActionTypeEnum itemActionType;

    @XmlAttribute(name = "hp_gain")
    private int hpGain;

    @XmlAttribute(name = "mp_gain")
    private int mpGain;

    @XmlAttribute(name = "stamina_gain")
    private int staminaGain;

    @XmlAttribute(name = "speed_gain")
    private int speedGain;

    @XmlAttribute(name = "skill_id")
    private int skillId;

    @XmlAttribute(name = "skill_level")
    private int skillLevel;

    @XmlAttribute(name = "effect_id")
    private int effectId;

    @XmlAttribute(name = "rate")
    private int rate;

    @XmlAttribute(name = "duration")
    private int duration;

    @XmlAttribute(name = "amount")
    private int amount;

    public ItemActionTypeEnum getItemActionType() {
        return itemActionType;
    }

    public int getHpGain() {
        return hpGain;
    }

    public int getMpGain() {
        return mpGain;
    }

    public int getStaminaGain() {
        return staminaGain;
    }

    public int getSpeedGain() {
        return speedGain;
    }

    public int getSkillId() {
        return skillId;
    }

    public int getSkillLevel() {
        return skillLevel;
    }

    public int getEffectId() {
        return effectId;
    }

    public int getRate() {
        return rate;
    }

    public int getDuration() {
        return duration;
    }

    public int getAmount() {
        return amount;
    }
}
