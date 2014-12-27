package com.angelis.tera.game.process.model.drop;

import com.angelis.tera.common.process.model.AbstractModel;
import com.angelis.tera.game.process.model.drop.enums.DropChanceEnum;
import com.angelis.tera.game.process.model.drop.enums.ItemCategoryEnum;

public class Drop extends AbstractModel {

    public Drop(final Integer id) {
        super(id);
    }
    
    public Drop() {
        super(null);
    }

    private DropChanceEnum dropChance;
    private ItemCategoryEnum itemCategory;
    private int maxAmount;
    private int minAmount;
    private int itemId;

    public DropChanceEnum getDropChance() {
        return dropChance;
    }

    public void setDropChance(final DropChanceEnum dropChance) {
        this.dropChance = dropChance;
    }

    public ItemCategoryEnum getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(final ItemCategoryEnum itemCategory) {
        this.itemCategory = itemCategory;
    }

    public int getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(final int maxAmount) {
        this.maxAmount = maxAmount;
    }

    public int getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(final int minAmount) {
        this.minAmount = minAmount;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(final int itemId) {
        this.itemId = itemId;
    }
}
