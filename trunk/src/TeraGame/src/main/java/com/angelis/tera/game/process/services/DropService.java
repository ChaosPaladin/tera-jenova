package com.angelis.tera.game.process.services;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javolution.util.FastList;
import javolution.util.FastMap;

import org.apache.log4j.Logger;

import com.angelis.tera.common.domain.mapper.MapperManager;
import com.angelis.tera.common.services.AbstractService;
import com.angelis.tera.common.utils.Rnd;
import com.angelis.tera.game.config.DropConfig;
import com.angelis.tera.game.domain.entity.xml.DropEntity;
import com.angelis.tera.game.domain.entity.xml.creatures.drop.CreatureDropEntity;
import com.angelis.tera.game.domain.entity.xml.creatures.drop.CreatureDropEntityHolder;
import com.angelis.tera.game.domain.entity.xml.gathers.GatherDropEntity;
import com.angelis.tera.game.domain.entity.xml.gathers.GatherDropEntityHolder;
import com.angelis.tera.game.domain.mapper.xml.DropMapper;
import com.angelis.tera.game.presentation.network.SystemMessages;
import com.angelis.tera.game.presentation.network.packet.server.SM_DROP_ITEM_LOOT;
import com.angelis.tera.game.process.model.creature.AbstractCreature;
import com.angelis.tera.game.process.model.creature.Creature;
import com.angelis.tera.game.process.model.drop.Drop;
import com.angelis.tera.game.process.model.drop.DropItem;
import com.angelis.tera.game.process.model.drop.enums.ItemCategoryEnum;
import com.angelis.tera.game.process.model.enums.ObjectFamilyEnum;
import com.angelis.tera.game.process.model.enums.StorageTypeEnum;
import com.angelis.tera.game.process.model.gather.Gather;
import com.angelis.tera.game.process.model.group.Group;
import com.angelis.tera.game.process.model.item.Item;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.model.visible.WorldPosition;
import com.angelis.tera.game.process.tasks.drop.DropItemGetFreeTask;

public class DropService extends AbstractService {

    /** LOGGER */
    private static Logger log = Logger.getLogger(DropService.class.getName());

    private final Map<Integer, List<Drop>> creatureDrops = new FastMap<>();
    private final Map<Integer, List<Drop>> gatherDrops = new FastMap<>();

    @Override
    public void onInit() {
        final Set<CreatureDropEntity> creatureDropEntities = XMLService.getInstance().getEntity(CreatureDropEntityHolder.class).getCreatureDrops();
        for (final CreatureDropEntity creatureDropEntity : creatureDropEntities) {
            final List<Drop> drops = new FastList<>();
            for (final DropEntity dropEntity : creatureDropEntity.getDrops()) {
                drops.add(MapperManager.getXMLMapper(DropMapper.class).map(dropEntity));
            }
            creatureDrops.put(creatureDropEntity.getCreatureFullId(), drops);
        }
        XMLService.getInstance().clearEntity(CreatureDropEntityHolder.class);
        
        final Set<GatherDropEntity> gatherDropEntities = XMLService.getInstance().getEntity(GatherDropEntityHolder.class).getGatherDrops();
        for (final GatherDropEntity gatherDropEntity : gatherDropEntities) {
            final List<Drop> drops = new FastList<>();
            for (final DropEntity dropEntity : gatherDropEntity.getDrops()) {
                drops.add(MapperManager.getXMLMapper(DropMapper.class).map(dropEntity));
            }
            gatherDrops.put(gatherDropEntity.getGatherId(), drops);
        }
        XMLService.getInstance().clearEntity(CreatureDropEntityHolder.class);

        log.info("DropService started");
    }

    @Override
    public void onDestroy() {
        creatureDrops.clear();
        log.info("DropService stopped");
    }

    public List<Drop> getDropFromCreature(final Creature creature, final ItemCategoryEnum itemCategory) {
        final List<Drop> drops = new FastList<>();

        final List<Drop> creatureDrops = this.creatureDrops.get(creature.getTemplate().getFullId());
        if (creatureDrops != null && !creatureDrops.isEmpty()) {
            for (final Drop drop : creatureDrops) {
                if (drop.getItemCategory() == itemCategory) {
                    drops.add(drop);
                }
            }
        }

        return drops;
    }
    
    public List<Drop> getDropFromGather(final Gather gather, final ItemCategoryEnum itemCategory) {
        final List<Drop> drops = new FastList<>();
        
        final List<Drop> gatherDrops = this.gatherDrops.get(gather.getId());
        if (gatherDrops != null && !gatherDrops.isEmpty()) {
            for (final Drop drop : gatherDrops) {
                if (drop.getItemCategory() == itemCategory) {
                    drops.add(drop);
                }
            }
        }
        
        return drops;
    }
    
    public final List<DropItem> generateDrop(final Creature creature, final Player player) {
        final List<DropItem> dropItems = new FastList<>();

        final List<Drop> drops = this.getDropFromCreature(creature, ItemCategoryEnum.NONE);
        if (drops == null) {
            return dropItems;
        }

        if (Rnd.chance(75)) {
            final int amount = (int) (10 * CreatureService.getInstance().getMoney(creature.getLevel()) / Rnd.get(20, 60));
            if (amount > 0) {
                dropItems.add(createDropItem(creature, player, Item.MONEY_ID, creature.getWorldPosition().clone(), amount));
            }
        }

        for (final Drop drop : drops) {
            int dropChance = Rnd.get(0, 100);
            dropChance *= DropConfig.DROP_CREATURE_RATE;
            if (dropChance > 100) {
                dropChance = 100;
            }

            if (drop.getDropChance().value > dropChance) {
                continue;
            }

            dropItems.add(createDropItem(creature, player, drop.getItemId(), creature.getWorldPosition().clone(), generateDropAmount(player, drop)));
        }

        return dropItems;
    }

    public int generateDropAmount(final Player player, final Drop drop) {
        int dropChance = Rnd.get(0, 100);
        dropChance *= DropConfig.DROP_CREATURE_RATE;
        if (dropChance > 100) {
            dropChance = 100;
        }
        
        if (drop.getDropChance().value > dropChance) {
            return drop.getMaxAmount();
        } else {
            return drop.getMinAmount();
        }
    }
    
    public void pickupItem(final Player player, final int dropItemUid) {
        final DropItem dropItem = (DropItem) ObjectIDService.getInstance().getObjectByUId(ObjectFamilyEnum.DROP_ITEM, dropItemUid);
        if (dropItem == null) {
            return;
        }

        if (!dropItem.isFree() && !dropItem.getOwner().equals(player)) {
            final Group group = dropItem.getOwner().getGroup();
            if (group == null || !group.equals(player.getGroup())) {
                player.getConnection().sendPacket(SystemMessages.THAT_IS_NOT_YOURS());
                return;
            }
        }

        if (StorageService.getInstance().addItem(player, player.getStorage(StorageTypeEnum.INVENTORY), dropItem.getItem().getId(), dropItem.getAmount())) {
            SpawnService.getInstance().despawnDropItem(dropItem);
            VisibleService.getInstance().sendPacketForVisible(player, new SM_DROP_ITEM_LOOT(player, dropItem, dropItem.getAmount()));
            ObjectIDService.getInstance().releaseId(ObjectFamilyEnum.DROP_ITEM, dropItemUid);
        }
    }

    private DropItem createDropItem(final AbstractCreature creature, final Player player, final int itemId, final WorldPosition worldPosition, final int amount) {
        final Item item = new Item(itemId);
        worldPosition.addX(Rnd.get(-50, 50));
        worldPosition.addY(Rnd.get(-50, 50));
        worldPosition.addZ(Rnd.get(-50, 50));

        final DropItem dropItem = new DropItem(null, creature, player, item, worldPosition, amount);
        ThreadPoolService.getInstance().scheduleTask(new DropItemGetFreeTask(dropItem), DropConfig.DROP_FREE_TIME, TimeUnit.SECONDS);
        return dropItem;
    }

    /** SINGLETON */
    public static DropService getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        protected static final DropService instance = new DropService();
    }
}
