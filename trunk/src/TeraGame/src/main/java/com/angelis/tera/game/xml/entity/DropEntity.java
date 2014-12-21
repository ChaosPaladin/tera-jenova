package com.angelis.tera.game.xml.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.angelis.tera.common.domain.entity.xml.AbstractXMLEntity;
import com.angelis.tera.game.process.model.drop.enums.DropChanceEnum;
import com.angelis.tera.game.process.model.drop.enums.ItemCategoryEnum;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="drop", namespace="http://angelis.com/base")
public class DropEntity extends AbstractXMLEntity {

    private static final long serialVersionUID = 7814511484052399726L;

    @XmlAttribute(name = "category", required = false)
    private ItemCategoryEnum itemCategory;
    
    @XmlAttribute(name = "chance")
    private DropChanceEnum dropChance;
    
    @XmlAttribute(name = "max_amount")
    private int maxAmount;
    
    @XmlAttribute(name = "min_amount")
    private int minAmount;
    
    @XmlAttribute(name = "item_id")
    private int itemId;

    public ItemCategoryEnum getItemCategory() {
        if (itemCategory == null) {
            itemCategory = ItemCategoryEnum.NONE;
        }
        return itemCategory;
    }

    public void setItemCategory(final ItemCategoryEnum itemCategory) {
        this.itemCategory = itemCategory;
    }

    public DropChanceEnum getDropChance() {
        return dropChance;
    }

    public void setDropChance(final DropChanceEnum dropChance) {
        this.dropChance = dropChance;
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
