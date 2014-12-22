package com.angelis.tera.game.process.model.item;

import com.angelis.tera.common.process.model.AbstractModel;
import com.angelis.tera.game.process.model.item.enums.ItemActionTypeEnum;

public class ItemAction extends AbstractModel {

    private ItemActionTypeEnum itemActionType;
    private int hpGain;
    private int mpGain;
    private int staminaGain;
    private int speedGain;
    private int skillId;
    private int skillLevel;
    private int effectId;
    private int rate;
    private int duration;
    private int amount;

    public ItemAction() {
        super(null);
    }

    public ItemActionTypeEnum getItemActionType() {
        return itemActionType;
    }

    public void setItemActionType(final ItemActionTypeEnum itemActionType) {
        this.itemActionType = itemActionType;
    }

    public int getHpGain() {
        return hpGain;
    }

    public void setHpGain(final int hpGain) {
        this.hpGain = hpGain;
    }

    public int getMpGain() {
        return mpGain;
    }

    public void setMpGain(final int mpGain) {
        this.mpGain = mpGain;
    }

    public int getStaminaGain() {
        return staminaGain;
    }

    public void setStaminaGain(final int staminaGain) {
        this.staminaGain = staminaGain;
    }

    public int getSpeedGain() {
        return speedGain;
    }

    public void setSpeedGain(final int speedGain) {
        this.speedGain = speedGain;
    }

    public int getSkillId() {
        return skillId;
    }

    public void setSkillId(final int skillId) {
        this.skillId = skillId;
    }

    public int getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(final int skillLevel) {
        this.skillLevel = skillLevel;
    }

    public int getEffectId() {
        return effectId;
    }

    public void setEffectId(final int effectId) {
        this.effectId = effectId;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(final int rate) {
        this.rate = rate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(final int duration) {
        this.duration = duration;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(final int amount) {
        this.amount = amount;
    }
}
