package com.angelis.tera.game.domain.entity.database;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import javolution.util.FastList;

import com.angelis.tera.common.domain.entity.database.AbstractDatabaseEntity;
import com.angelis.tera.game.process.model.enums.StorageTypeEnum;

@Entity
@Table(name = "storages")
public class StorageEntity extends AbstractDatabaseEntity {

    private static final long serialVersionUID = 5090455704132338034L;

    @Column
    private StorageTypeEnum storageType;

    @Column
    private int size;

    @Column
    private int money;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "storage")
    private List<StorageItemEntity> storageItems;

    public StorageEntity(final Integer id, final StorageTypeEnum storageType) {
        super(id);
        this.storageType = storageType;
    }

    public StorageEntity(final StorageTypeEnum storageType) {
        super();
        this.storageType = storageType;
    }

    public StorageEntity(final Integer id) {
        this(id, null);
    }

    public StorageEntity() {
        super();
    }

    public StorageTypeEnum getStorageType() {
        return storageType;
    }

    public void setStorageType(final StorageTypeEnum storageType) {
        this.storageType = storageType;
    }

    public int getSize() {
        return size;
    }

    public void setSize(final int size) {
        this.size = size;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(final int money) {
        this.money = money;
    }

    public List<StorageItemEntity> getStorageItems() {
        if (storageItems == null) {
            storageItems = new FastList<StorageItemEntity>();
        }
        return storageItems;
    }

    public void setStorageItems(final List<StorageItemEntity> storageItems) {
        this.storageItems = storageItems;
    }

    public void addStorageItem(final StorageItemEntity storageItem) {
        this.getStorageItems().add(storageItem);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = prime * size;
        result = prime * result + ((storageType == null) ? 0 : storageType.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof StorageEntity)) {
            return false;
        }
        final StorageEntity other = (StorageEntity) obj;
        if (size != other.size) {
            return false;
        }
        if (storageType != other.storageType) {
            return false;
        }
        return true;
    }
}
