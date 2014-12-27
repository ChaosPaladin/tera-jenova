package com.angelis.tera.game.process.services;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javolution.util.FastList;
import javolution.util.FastSet;

import org.apache.log4j.Logger;

import com.angelis.tera.common.services.AbstractService;
import com.angelis.tera.game.domain.entity.xml.players.PlayerItemEntity;
import com.angelis.tera.game.domain.entity.xml.players.PlayerItemSetEntity;
import com.angelis.tera.game.domain.entity.xml.players.PlayerItemSetEntityHolder;
import com.angelis.tera.game.presentation.network.SystemMessages;
import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.server.SM_INVENTORY;
import com.angelis.tera.game.presentation.network.packet.server.player.SM_PLAYER_APPEARANCE_CHANGE;
import com.angelis.tera.game.presentation.network.packet.server.player.SM_PLAYER_GATHER_STATS;
import com.angelis.tera.game.presentation.network.packet.server.player.SM_PLAYER_INVENTORY_SLOT_CHANGED;
import com.angelis.tera.game.presentation.network.packet.server.player.SM_PLAYER_STATE;
import com.angelis.tera.game.presentation.network.packet.server.player.SM_PLAYER_STATS_UPDATE;
import com.angelis.tera.game.process.model.enums.StorageTypeEnum;
import com.angelis.tera.game.process.model.item.Item;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.model.storage.Storage;
import com.angelis.tera.game.process.model.storage.StorageItem;
import com.angelis.tera.game.process.model.storage.enums.InventorySizeEnum;

public class StorageService extends AbstractService {

    /** LOGGER */
    private static Logger log = Logger.getLogger(StorageService.class.getName());

    @Override
    public void onInit() {
        log.info("StorageService started");
    }

    @Override
    public void onDestroy() {
        log.info("StorageService stopped");
    }

    public void onPlayerCreate(final Player player) {
        final Set<Storage> storages = new FastSet<>();
        final Storage inventoryStorage = new Storage(StorageTypeEnum.INVENTORY);
        
        switch (player.getPlayerClass()) {
            case REAPER:
                inventoryStorage.setSize(InventorySizeEnum.SECOND.value);
            break;
            
            default:
                inventoryStorage.setSize(InventorySizeEnum.FIRST.value);
        }

        storages.add(inventoryStorage);

        storages.add(new Storage(StorageTypeEnum.PLAYER_WAREHOUSE));
        player.setStorages(storages);

        final PlayerItemSetEntity itemset = XMLService.getInstance().getEntity(PlayerItemSetEntityHolder.class).getPlayerItemsetsByTargetClass(player.getPlayerClass());
        for (final PlayerItemEntity itemEntity : itemset.getItems()) {
            final StorageItem storageItem = new StorageItem();
            final Item item = new Item(itemEntity.getItemId());
            storageItem.setItem(item);
            storageItem.setCount(1);

            inventoryStorage.addStorageItem(itemEntity.getSlot().value, storageItem);
        }

        this.addItem(player, inventoryStorage, 125, 5);
        this.addItem(player, inventoryStorage, 8007, 3);
    }

    public void onPlayerOpenStorage(final Player player, final StorageTypeEnum playerWarehouse) {
        // TODO Auto-generated method stub
    }

    public boolean addItem(final Player player, final Storage storage, final int itemId, final int itemAmount, final String creatorName) {
        if (itemId == Item.MONEY_ID) {
            storage.addMoney(itemAmount);
            player.getConnection().sendPacket(SystemMessages.MONEY_ADD(String.valueOf(itemAmount)));
            return true;
        }

        StorageItem storageItem = storage.getStorageItemByItemId(itemId);
        final TeraGameConnection connection = player.getConnection();
        if (storageItem == null || creatorName != null) {
            if (storage.getFirstFreeSlot() == -1) {
                connection.sendPacket(SystemMessages.INVENTORY_IS_FULL());
                return false;
            }

            storageItem = new StorageItem();
            storageItem.setItem(new Item(itemId));
            storageItem.setCreatorName(creatorName);
            storage.addStorageItem(storageItem);
        }

        storageItem.addCount(itemAmount);

        if (player.isOnline()) {
            connection.sendPacket(SystemMessages.ITEM_ADD(player.getName(), String.valueOf(itemId), String.valueOf(itemAmount)));
            this.showStorage(player, storage, false);
            connection.sendPacket(new SM_PLAYER_INVENTORY_SLOT_CHANGED(Arrays.asList(storage.getSlot(storageItem))));
        }

        QuestService.getInstance().onPlayerItemPickup(player, storageItem.getItem());
        return true;
    }

    public boolean addItem(final Player player, final Storage storage, final int itemId, final int itemAmount) {
        return this.addItem(player, storage, itemId, itemAmount, null);
    }

    public boolean removeItem(final Player player, final Storage storage, final int itemId, final int itemAmount) {
        final StorageItem storageItemSlotFrom = storage.getStorageItemByItemId(itemId);
        if (storageItemSlotFrom == null) {
            return false;
        }

        final int newCount = storageItemSlotFrom.getCount() - itemAmount;
        if (newCount <= 0) {
            storage.removeItem(storageItemSlotFrom);
        }
        else {
            storageItemSlotFrom.setCount(newCount);
        }

        final TeraGameConnection connection = player.getConnection();
        connection.sendPacket(SystemMessages.ITEM_NEW_AMOUNT(String.valueOf(itemId), String.valueOf(itemAmount)));

        this.showStorage(player, storage, false);
        connection.sendPacket(new SM_PLAYER_INVENTORY_SLOT_CHANGED(Arrays.asList(storage.getSlot(storageItemSlotFrom))));

        return true;
    }

    public void removeItem(final Player player, final Storage storage, final StorageItem storageItem, final int itemAmount) {
        this.removeItem(player, storage, storageItem.getItem().getId(), itemAmount);
    }

    public void removeItemBySlot(final Player player, final Storage storage, final int slot, final int itemAmount) {
        final StorageItem storageItem = storage.getStorageItemBySlot(slot);
        if (storageItem == null) {
            return;
        }

        this.removeItem(player, storage, storageItem.getItem().getId(), itemAmount);
    }

    public void removeItem(final Player player, final StorageTypeEnum storageType, final Item item, final int itemAmount) {
        final Storage storage = player.getStorage(storageType);
        if (storage == null) {
            return;
        }

        this.removeItem(player, storage, item.getId(), itemAmount);
    }

    public void moveItem(final Player player, final Storage storage, final int fromSlot, int toSlot) {
        final StorageItem storageItemSlotFrom = storage.getStorageItemBySlot(fromSlot);
        if (storageItemSlotFrom == null) {
            // cheat ?
            return;
        }

        // Player equip or unequip item with right click
        final boolean isEquip = (toSlot <= 39);
        final boolean isUnequip = (fromSlot <= 39 && toSlot > 39);
        if (isEquip) {
            if (fromSlot < 39) {
                toSlot = storage.getFirstFreeSlot();
            }
        }

        if (fromSlot < 40 && toSlot < 40) {
            // TODO if player want to permute earing or ring
            // Player tryed to change equiped item with another
            return;
        }

        final StorageItem storageItemSlotTo = storage.getStorageItemBySlot(toSlot);
        if (storageItemSlotTo != null) {
            if (!storageItemSlotTo.getItem().equals(storageItemSlotFrom.getItem()) || isEquip) {
                storage.setStorageItem(toSlot, storageItemSlotFrom);
                storage.setStorageItem(fromSlot, storageItemSlotTo);
            }
            else {
                storageItemSlotTo.addCount(storageItemSlotFrom.getCount());
                storage.removeItem(fromSlot);
            }
        }
        else {
            storage.removeItem(fromSlot);
            storage.setStorageItem(toSlot, storageItemSlotFrom);
        }

        if (toSlot == 0) {
            toSlot = storage.getFirstFreeSlot();
            if (toSlot == -1) {
                return;
            }
        }

        final TeraGameConnection connection = player.getConnection();
        if (isEquip || isUnequip) {
            connection.sendPacket(new SM_PLAYER_STATS_UPDATE(player));
            connection.sendPacket(new SM_PLAYER_GATHER_STATS(player.getGatherStats()));
            connection.sendPacket(new SM_PLAYER_STATE(player));
        }

        this.showStorage(player, storage, !isEquip && !isUnequip);
        connection.sendPacket(new SM_PLAYER_INVENTORY_SLOT_CHANGED(Arrays.asList(fromSlot, toSlot)));
        VisibleService.getInstance().sendPacketForVisible(player, new SM_PLAYER_APPEARANCE_CHANGE(player));
    }

    public void moveItem(final Player player, final Storage storage, final int itemId, final int fromSlot, final int toSlot) {
        final StorageItem storageItemSlotFrom = storage.getStorageItemBySlot(fromSlot);
        if (storageItemSlotFrom == null || storageItemSlotFrom.getItem().getId() != itemId) {
            // cheat ?
            return;
        }

        this.moveItem(player, storage, fromSlot, toSlot);
    }

    public void upgradeInventory(final Player player, final Storage storage) {
        final int inventorySize = storage.getSize();
        storage.setSize(InventorySizeEnum.fromValue(inventorySize + 1).value);
        this.showStorage(player, storage, true);
    }

    public void downgradeInventory(final Player player, final Storage storage) {
        final int inventorySize = storage.getSize();
        storage.setSize(InventorySizeEnum.fromValue(inventorySize - 1).value);
        this.showStorage(player, storage, true);
    }

    public void reorderStorage(final Player player, final Storage storage) {
        final List<Entry<Integer, StorageItem>> list = new FastList<>(storage.getStorageItemsAfterSlot(39).entrySet());
        Collections.sort(list, new Comparator<Entry<Integer, StorageItem>>() {
            @Override
            public int compare(final Entry<Integer, StorageItem> o1, final Entry<Integer, StorageItem> o2) {
                // TODO WEAPONs & ARMORS are first
                return Integer.valueOf(o2.getValue().getCount()).compareTo(Integer.valueOf(o1.getValue().getCount()));
            }
        });

        storage.removeStorageItemsAfterSlot(39);
        final Map<Integer, StorageItem> storageItems = storage.getStorageItems();
        for (int i = 0; i < list.size(); i++) {
            storageItems.put(40 + i, list.get(i).getValue());
        }

        this.showStorage(player, storage, true);
    }

    public final void showStorage(final Player player, final Storage storage, final boolean showInventory) {
        switch (storage.getStorageType()) {
            case INVENTORY:
                player.getConnection().sendPacket(new SM_INVENTORY(player, showInventory));
            break;

            default:
                throw new RuntimeException("TODO");
        }
    }

    /** SINGLETON */
    public static StorageService getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        protected static final StorageService instance = new StorageService();
    }
}
