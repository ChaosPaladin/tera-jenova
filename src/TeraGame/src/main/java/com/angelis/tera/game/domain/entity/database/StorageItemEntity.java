package com.angelis.tera.game.domain.entity.database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.angelis.tera.common.domain.entity.database.AbstractDatabaseEntity;

@Entity
@Table(name = "storage_items")
public class StorageItemEntity extends AbstractDatabaseEntity {

    private static final long serialVersionUID = 4583410644473722142L;

    @Column
    private int itemId;

    @Column
    private int slot;

    @Column
    private int count;

    @Column
    private int color;
    
    @Column
    private String creatorName;
    
    @ManyToOne()
    private StorageEntity storage;

    public StorageItemEntity(final Integer id) {
        super(id);
    }
    
    public StorageItemEntity() {
        super();
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(final int itemId) {
        this.itemId = itemId;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(final int slot) {
        this.slot = slot;
    }

    public int getCount() {
        return count;
    }

    public void setCount(final int count) {
        this.count = count;
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

    public StorageEntity getStorage() {
        return storage;
    }

    public void setStorage(final StorageEntity storage) {
        this.storage = storage;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = prime * color;
        result = prime * result + count;
        result = prime * result + ((creatorName == null) ? 0 : creatorName.hashCode());
        result = prime * result + itemId;
        result = prime * result + slot;
        result = prime * result + ((storage == null) ? 0 : storage.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof StorageItemEntity)) {
            return false;
        }
        final StorageItemEntity other = (StorageItemEntity) obj;
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
        if (itemId != other.itemId) {
            return false;
        }
        if (slot != other.slot) {
            return false;
        }
        if (storage == null) {
            if (other.storage != null) {
                return false;
            }
        }
        else if (!storage.equals(other.storage)) {
            return false;
        }
        return true;
    }
    
    
}
