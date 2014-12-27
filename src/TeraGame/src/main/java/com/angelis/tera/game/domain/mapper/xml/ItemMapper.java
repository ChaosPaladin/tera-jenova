package com.angelis.tera.game.domain.mapper.xml;

import java.util.Set;

import javolution.util.FastSet;

import com.angelis.tera.common.domain.mapper.MapperManager;
import com.angelis.tera.common.domain.mapper.xml.AbstractXMLMapper;
import com.angelis.tera.game.domain.entity.xml.items.ItemActionEntity;
import com.angelis.tera.game.domain.entity.xml.items.ItemEntity;
import com.angelis.tera.game.process.model.item.Item;
import com.angelis.tera.game.process.model.item.ItemAction;

public class ItemMapper extends AbstractXMLMapper<ItemEntity, Item> {

    @Override
    protected Item createNewEmptyModel() {
        return new Item();
    }

    @Override
    public void map(final ItemEntity entity, final Item model) {
        model.setId(entity.getItemId());
    }

    @Override
    protected void finalizeDependencies(final ItemEntity entity, final Item model) {
        // ITEM ACTION
        final ItemActionMapper itemActionMapper = MapperManager.getXMLMapper(ItemActionMapper.class);
        final Set<ItemAction> itemActions = new FastSet<>();
        for (final ItemActionEntity itemActionEntity : entity.getItemActions()) {
            itemActions.add(itemActionMapper.map(itemActionEntity));
        }
        model.setItemActions(itemActions);
    }
}
