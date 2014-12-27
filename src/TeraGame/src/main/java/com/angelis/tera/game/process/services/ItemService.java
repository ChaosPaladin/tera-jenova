package com.angelis.tera.game.process.services;

import java.util.Map;

import javolution.util.FastMap;

import org.apache.log4j.Logger;

import com.angelis.tera.common.domain.mapper.MapperManager;
import com.angelis.tera.common.process.services.AbstractService;
import com.angelis.tera.game.domain.entity.xml.items.ItemEntity;
import com.angelis.tera.game.domain.entity.xml.items.ItemEntityHolder;
import com.angelis.tera.game.domain.mapper.xml.ItemMapper;
import com.angelis.tera.game.presentation.network.packet.server.SM_ITEM_INFO;
import com.angelis.tera.game.process.model.campfire.CampFire;
import com.angelis.tera.game.process.model.enums.StorageTypeEnum;
import com.angelis.tera.game.process.model.item.Item;
import com.angelis.tera.game.process.model.item.ItemAction;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.model.storage.Storage;
import com.angelis.tera.game.process.model.storage.StorageItem;
import com.angelis.tera.game.process.model.storage.enums.ViewModeEnum;
import com.angelis.tera.game.process.model.visible.WorldPosition;

public class ItemService extends AbstractService {

    /** LOGGER */
    private static Logger log = Logger.getLogger(ItemService.class.getName());

    private final Map<Integer, Item> items = new FastMap<>();

    @Override
    public void onInit() {
        final ItemMapper itemMapper = MapperManager.getXMLMapper(ItemMapper.class);
        for (final ItemEntity itemEntity : XMLService.getInstance().getEntity(ItemEntityHolder.class).getItems()) {
            this.items.put(itemEntity.getItemId(), itemMapper.map(itemEntity));
        }
        XMLService.getInstance().clearEntity(ItemEntityHolder.class);

        log.info("ItemService started");
    }

    @Override
    public void onDestroy() {
        this.items.clear();
        log.info("ItemService stopped");
    }

    public void showItemInfo(final Player player, final ViewModeEnum viewMode, final Item item, final String playerName) {
        if (!player.getName().equals(playerName)) {
            return;
        }
        
        final Storage storage = player.getStorage(StorageTypeEnum.INVENTORY);
        final StorageItem storageItem = storage.getStorageItemByItemId(item.getId());
        if (storageItem == null) {
            return;
        }
        
        player.getConnection().sendPacket(new SM_ITEM_INFO(storageItem));
    }

    public void onPlayerItemUse(final Player player, final int itemId, final WorldPosition worldPosition) {
        final Storage storage = player.getStorage(StorageTypeEnum.INVENTORY);
        final StorageItem storageItem = storage.getStorageItemByItemId(itemId);
        if (storageItem == null) {
            return;
        }

        final Item item = this.items.get(itemId);
        if (item == null) {
            log.error("No item template found for item_id "+itemId);
            return;
        }
        
        if (!this.checkCanUseItem(player, item)) {
            return;
        }

        for (final ItemAction itemAction : item.getItemActions()) {
            final boolean mustRemoveItem = itemAction.getItemActionType().doAction(item, itemAction, player, worldPosition);
            if (mustRemoveItem) {
                StorageService.getInstance().removeItem(player, StorageTypeEnum.INVENTORY, item, 1);
            }
        }
    }

    public final boolean checkCanSpawnFireCamp(final Player player, final WorldPosition worldPosition) {
        if (player.getActiveCampFire() != null) {
            return false;
        }
        
        final CampFire nearestCampFire = SpawnService.getInstance().getNearestCampFire(worldPosition);
        if (nearestCampFire != null && nearestCampFire.getWorldPosition().distanceTo(worldPosition) < 1000) {
            return false;
        }
        
        return true;
    }

    private final boolean checkCanUseItem(final Player player, final Item item) {
        // TODO
        return true;
    }

    /** SINGLETON */
    public static ItemService getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        protected static final ItemService instance = new ItemService();
    }
}
