package com.angelis.tera.game.process.model.item;

import java.util.Set;

import javolution.util.FastSet;

import com.angelis.tera.game.process.model.AbstractUniqueTeraModel;

public class Item extends AbstractUniqueTeraModel {

    public static final int MONEY_ID = 20000000;
    
    private Set<ItemAction> itemActions;

    public Item(final Integer id) {
        super(id);
    }

    public Item() {
        super(null);
    }

    public Set<ItemAction> getItemActions() {
        if (itemActions == null) {
            itemActions = new FastSet<>(0);
        }
        return itemActions;
    }

    public void setItemActions(final Set<ItemAction> itemActions) {
        this.itemActions = itemActions;
    }
}
