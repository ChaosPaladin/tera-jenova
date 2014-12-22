package com.angelis.tera.game.process.services;

import java.util.Map;

import javolution.util.FastMap;

import org.apache.log4j.Logger;

import com.angelis.tera.common.services.AbstractService;
import com.angelis.tera.game.domain.entity.xml.items.ItemActionEntity;
import com.angelis.tera.game.domain.entity.xml.items.ItemTemplateEntity;
import com.angelis.tera.game.domain.entity.xml.items.ItemTemplateEntityHolder;
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
        for (final ItemTemplateEntity itemTemplateEntity : XMLService.getInstance().getEntity(ItemTemplateEntityHolder.class).getItemTemplates()) {
            final Item item = new Item(itemTemplateEntity.getItemId());
            for (final ItemActionEntity itemActionEntity : itemTemplateEntity.getItemActions()) {
                final ItemAction itemAction = new ItemAction();
                itemAction.setItemActionType(itemActionEntity.getItemActionType());
                itemAction.setHpGain(itemActionEntity.getHpGain());
                itemAction.setMpGain(itemActionEntity.getMpGain());
                itemAction.setStaminaGain(itemActionEntity.getStaminaGain());
                itemAction.setSpeedGain(itemActionEntity.getSpeedGain());
                itemAction.setSkillId(itemActionEntity.getSkillId());
                itemAction.setSkillLevel(itemActionEntity.getSkillLevel());
                itemAction.setEffectId(itemActionEntity.getEffectId());
                itemAction.setRate(itemActionEntity.getRate());
                itemAction.setDuration(itemActionEntity.getDuration());
                item.getItemActions().add(itemAction);
            }
            this.items.put(itemTemplateEntity.getItemId(), item);
        }
        XMLService.getInstance().clearEntity(ItemTemplateEntityHolder.class);

        log.info("ItemService started");
    }

    @Override
    public void onDestroy() {
        log.info("ItemService started");
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
            log.error("No item_template for item_id "+itemId);
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
