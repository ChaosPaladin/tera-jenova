package com.angelis.tera.game.process.model.storage;

import com.angelis.tera.common.process.model.AbstractModel;
import com.angelis.tera.game.process.model.item.Item;

public class StorageItem extends AbstractModel {

    public StorageItem(final Integer id) {
        super(id);
    }

    public StorageItem() {
        super(null);
    }

    private Item item;
    private int count;
    private int color;
    private String creatorName;

    public Item getItem() {
        return item;
    }

    public void setItem(final Item item) {
        this.item = item;
    }

    public int getCount() {
        return count;
    }

    public void setCount(final int count) {
        this.count = count;
    }
    
    public void addCount(final int count) {
        this.count += count;
    }

    public int getColor() {
        return color;
    }

    public void setColor(final int color) {
        this.color = color;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(final String creatorName) {
        this.creatorName = creatorName;
    }

    public final int getItemId() {
        return this.item != null ? this.item.getId() : 0;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + color;
        result = prime * result + count;
        result = prime * result + ((creatorName == null) ? 0 : creatorName.hashCode());
        result = prime * result + ((item == null) ? 0 : item.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof StorageItem)) {
            return false;
        }
        final StorageItem other = (StorageItem) obj;
        if (color != other.color) {
            return false;
        }
        if (count != other.count) {
            return false;
        }
        if (creatorName == null) {
            if (other.creatorName != null) {
                return false;
            }
        }
        else if (!creatorName.equals(other.creatorName)) {
            return false;
        }
        if (item == null) {
            if (other.item != null) {
                return false;
            }
        }
        else if (!item.equals(other.item)) {
            return false;
        }
        return true;
    }
}
