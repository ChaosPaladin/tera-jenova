package com.angelis.tera.game.process.model.storage;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javolution.util.FastMap;

import com.angelis.tera.common.process.model.AbstractModel;
import com.angelis.tera.game.process.model.enums.StorageTypeEnum;
import com.angelis.tera.game.process.model.storage.enums.InventorySlotEnum;

public class Storage extends AbstractModel {

    public static final StorageItem NEW_STORAGE_ITEM = new StorageItem(0);
    
    private StorageTypeEnum storageType;
    private Map<Integer, StorageItem> storageItems;
    private int size;
    private int money;

    public Storage() {
        super(null);
    }
    
    public Storage(final Integer abstractId) {
        super(abstractId);
    }

    public Storage(final StorageTypeEnum storageType) {
        super(null);
        this.storageType = storageType;
    }

    public StorageTypeEnum getStorageType() {
        return storageType;
    }

    public void setStorageType(final StorageTypeEnum storageType) {
        this.storageType = storageType;
    }

    public Map<Integer, StorageItem> getStorageItems() {
        if (storageItems == null) {
            storageItems = new FastMap<>();
        }
        return storageItems;
    }

    public void setStorageItems(final Map<Integer, StorageItem> storageItems) {
        this.storageItems = storageItems;
    }

    public void addStorageItem(final int slot, final StorageItem storageItem) {
        this.getStorageItems().put(slot, storageItem);
    }

    public boolean addStorageItem(final StorageItem storageItem) {
        final int slot = this.getFirstFreeSlot();
        if (slot == -1) {
            return false;
        }

        this.addStorageItem(slot, storageItem);
        return true;
    }
    
    public void removeItem(final int slot) {
        this.storageItems.remove(slot);
    }
    
    public void removeItem(final StorageItem storageItem) {
        final int slot = this.getSlot(storageItem);
        if (slot == -1) {
            return;
        }
        
        this.removeItem(slot);
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
    
    public void addMoney(final int money) {
        this.money += money;
    }
    
    public void removeMoney(final int money) {
        this.money -= money;
    }

    public int getFirstFreeSlot() {
        for (int i = 0 ; i < this.size ; i++) {
            int slot = i;
            if (this.storageType == StorageTypeEnum.INVENTORY) {
                slot += 42;
            }

            if (this.isFreeSlot(slot)) {
                return slot;
            }
        }

        return -1;
    }

    private boolean isFreeSlot(final int slot) {
        return this.getStorageItemBySlot(slot) == null;
    }

    public StorageItem getStorageItemByInventorySlot(final InventorySlotEnum inventorySlot, final StorageItem defaultValue) {
        if (inventorySlot == null) {
            return null;
        }
        
        StorageItem storageItem = this.getStorageItemBySlot(inventorySlot.value);
        if (storageItem == null) {
            storageItem = defaultValue;
        }
        return storageItem;
    }
    
    public StorageItem getStorageItemByInventorySlot(final InventorySlotEnum inventorySlot) {
        return this.getStorageItemByInventorySlot(inventorySlot, null);
    }

    public int getStorageItemIdByInventorySlot(final InventorySlotEnum inventorySlot) {
        if (inventorySlot == null) {
            return 0;
        }
        
        return this.getStorageItemBySlot(inventorySlot.value).getItem().getId();
    }

    public StorageItem getStorageItemBySlot(final int slot) {
        return this.storageItems.get(slot);
    }

    public Map<InventorySlotEnum, StorageItem> getStorageItems(final EnumSet<InventorySlotEnum> inventorySlots) {
        final Map<InventorySlotEnum, StorageItem> storageItems = new LinkedHashMap<>();
        for (final InventorySlotEnum inventorySlot : inventorySlots) {
            storageItems.put(inventorySlot, this.storageItems.get(inventorySlot.value));
        }
        return storageItems;
    }

    public StorageItem getStorageItemByItemId(final int itemId) {
        for (final StorageItem storageItem : this.storageItems.values()) {
            if (storageItem.getItem().getId() == itemId) {
                return storageItem;
            }
        }
        return null;
    }
    
    public Map<Integer, StorageItem> getStorageItemsAfterSlot(final int slot) {
        final Map<Integer, StorageItem> storageItems = new FastMap<>();
        for (final Entry<Integer, StorageItem> entry : this.storageItems.entrySet()) {
            if (entry.getKey() >= slot) {
                storageItems.put(entry.getKey(), entry.getValue());
            }
        }
        return storageItems;
    }
    
    public void removeStorageItemsAfterSlot(final int slot) {
        final Iterator<Entry<Integer, StorageItem>> itr = this.storageItems.entrySet().iterator();
        while (itr.hasNext()) {
            final Entry<Integer, StorageItem> entry = itr.next();
            if (entry.getKey() >= slot) {
                itr.remove();
            }
        }
    }
    
    public int getSlot(final StorageItem storageItem) {
        for (final Entry<Integer, StorageItem> entry : this.storageItems.entrySet()) {
            if (entry.getValue().equals(storageItem)) {
                return entry.getKey();
            }
        }
        return -1;
    }

    public void setStorageItem(final int slot, final StorageItem storageItem) {
        storageItems.put(slot, storageItem);
    }
    
    public int getMaxEquipedLevel(final boolean onlyEquiped) {
        if (onlyEquiped) {
            return 0;
        }
        return 1;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 0;
        result = prime * result + size;
        result = prime * result + ((storageItems == null) ? 0 : storageItems.hashCode());
        result = prime * result + ((storageType == null) ? 0 : storageType.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Storage)) {
            return false;
        }
        final Storage other = (Storage) obj;
        if (size != other.size) {
            return false;
        }
        if (storageItems == null) {
            if (other.storageItems != null) {
                return false;
            }
        }
        else if (!storageItems.equals(other.storageItems)) {
            return false;
        }
        if (storageType != other.storageType) {
            return false;
        }
        return true;
    }
}
