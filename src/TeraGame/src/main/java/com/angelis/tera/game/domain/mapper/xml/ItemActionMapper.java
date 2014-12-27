package com.angelis.tera.game.domain.mapper.xml;

import com.angelis.tera.common.domain.mapper.xml.AbstractXMLMapper;
import com.angelis.tera.game.domain.entity.xml.items.ItemActionEntity;
import com.angelis.tera.game.process.model.item.ItemAction;

public class ItemActionMapper extends AbstractXMLMapper<ItemActionEntity, ItemAction>  {

    @Override
    protected ItemAction createNewEmptyModel() {
        return new ItemAction();
    }

    @Override
    public void map(final ItemActionEntity entity, final ItemAction model) {
        model.setItemActionType(entity.getItemActionType());
        model.setHpGain(entity.getHpGain());
        model.setMpGain(entity.getMpGain());
        model.setStaminaGain(entity.getStaminaGain());
        model.setSpeedGain(entity.getSpeedGain());
        model.setSkillId(entity.getSkillId());
        model.setSkillLevel(entity.getSkillLevel());
        model.setEffectId(entity.getEffectId());
        model.setRate(entity.getRate());
        model.setDuration(entity.getDuration());
        model.setAmount(entity.getAmount());
    }

    @Override
    protected void finalizeDependencies(final ItemActionEntity entity, final ItemAction model) {
        // No dependency
    }

}
