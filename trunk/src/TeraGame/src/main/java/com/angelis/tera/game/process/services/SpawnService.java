package com.angelis.tera.game.process.services;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javolution.util.FastList;
import javolution.util.FastSet;

import org.apache.log4j.Logger;

import com.angelis.tera.common.process.services.AbstractService;
import com.angelis.tera.game.config.SpawnConfig;
import com.angelis.tera.game.domain.entity.xml.SpawnEntity;
import com.angelis.tera.game.domain.entity.xml.creatures.spawn.CreatureSpawnEntity;
import com.angelis.tera.game.domain.entity.xml.creatures.spawn.CreatureSpawnsEntity;
import com.angelis.tera.game.domain.entity.xml.creatures.spawn.CreatureSpawnsEntityHolder;
import com.angelis.tera.game.domain.entity.xml.gathers.GatherSpawnEntity;
import com.angelis.tera.game.domain.entity.xml.gathers.GatherSpawnEntityHolder;
import com.angelis.tera.game.process.model.campfire.CampFire;
import com.angelis.tera.game.process.model.creature.Creature;
import com.angelis.tera.game.process.model.creature.Monster;
import com.angelis.tera.game.process.model.creature.Npc;
import com.angelis.tera.game.process.model.drop.DropItem;
import com.angelis.tera.game.process.model.enums.SpawnForEventEnum;
import com.angelis.tera.game.process.model.gameobject.GameObject;
import com.angelis.tera.game.process.model.gather.Gather;
import com.angelis.tera.game.process.model.template.CreatureTemplate;
import com.angelis.tera.game.process.model.template.GatherTemplate;
import com.angelis.tera.game.process.model.visible.VisibleTeraObject;
import com.angelis.tera.game.process.model.visible.WorldPosition;
import com.angelis.tera.game.process.tasks.campfire.CampFireDespawnTask;
import com.angelis.tera.game.process.tasks.creature.CreatureRespawnTask;
import com.angelis.tera.game.process.tasks.gather.GatherRespawnTask;

public class SpawnService extends AbstractService {

    /** LOGGER */
    private static Logger log = Logger.getLogger(SpawnService.class.getName());

    private final List<Creature> creatureSpawns = Collections.synchronizedList(new FastList<Creature>());
    private final List<Gather> gatherSpawns = Collections.synchronizedList(new FastList<Gather>());
    private final List<CampFire> campFireSpawns = Collections.synchronizedList(new FastList<CampFire>());
    private final List<DropItem> dropItemSpawns = Collections.synchronizedList(new FastList<DropItem>());
    private final List<GameObject> gameObjectSpawns = Collections.synchronizedList(new FastList<GameObject>());

    private SpawnService() {
    }

    @Override
    public void onInit() {
        // CREATURE SPAWNS
        final Set<CreatureSpawnsEntity> creatureEntities = XMLService.getInstance().getEntity(CreatureSpawnsEntityHolder.class).getCreatures();
        for (final CreatureSpawnsEntity creatureEntity : creatureEntities) {
            // Check if the creature can be spawned for the current event
            final SpawnForEventEnum spawnForEvent = creatureEntity.getSpawnForEvent();
            if (spawnForEvent != null) {
                if (spawnForEvent != SpawnConfig.SPAWN_CURRENT_EVENT) {
                    continue;
                }
            }

            final CreatureTemplate template = TemplateService.getInstance().getCreatureTemplateByFullId(creatureEntity.getFullId());
            if (template == null) {
                continue;
            }

            for (final CreatureSpawnEntity spawnEntity : creatureEntity.getSpawns()) {
                Creature teraCreature = null;
                if (template.isOffensive()) {
                    teraCreature = new Monster(creatureEntity.getId());
                } else {
                    teraCreature = new Npc(creatureEntity.getId());
                }
                
                TemplateService.getInstance().affectCreatureTemplate(teraCreature, template);

                BaseStatService.getInstance().affectVisibleObjectBaseStats(teraCreature, template.getBaseStats());
                BaseStatService.getInstance().affectCreatureCurrentStats(teraCreature);
                BaseStatService.getInstance().affectCreatureBonusStats(teraCreature);
                
                // World position
                final WorldPosition worldPosition = new WorldPosition(spawnEntity.getMapId(), spawnEntity.getX(), spawnEntity.getY(), spawnEntity.getZ(), spawnEntity.getHeading());
                teraCreature.setWorldPosition(worldPosition);
                
                this.creatureSpawns.add(teraCreature);
            }
        }
        XMLService.getInstance().clearEntity(CreatureSpawnsEntityHolder.class);
        clearDuplicate(this.creatureSpawns);

        // GATHER SPAWNS
        final Set<GatherSpawnEntity> gatherEntities = XMLService.getInstance().getEntity(GatherSpawnEntityHolder.class).getGatherSpawns();
        for (final GatherSpawnEntity gatherEntity : gatherEntities) {
            final GatherTemplate template = TemplateService.getInstance().getGatherTemplateById(gatherEntity.getId());
            if (template == null) {
                continue;
            }
            
            for (final SpawnEntity spawnEntity : gatherEntity.getSpawns()) {
                final Gather gather = new Gather(gatherEntity.getId());
                gather.setWorldPosition(new WorldPosition(spawnEntity.getMapId(), spawnEntity.getX(), spawnEntity.getY(), spawnEntity.getZ()));
                
                this.gatherSpawns.add(gather);
            }
        }
        XMLService.getInstance().clearEntity(GatherSpawnEntityHolder.class);
        clearDuplicate(this.gatherSpawns);

        log.info("SpawnService started");
    }

    @Override
    public void onDestroy() {
        this.creatureSpawns.clear();
        this.gatherSpawns.clear();
        this.campFireSpawns.clear();
        log.info("SpawnService stopped");
    }

    /********************
     * CREATURES
     ********************/
    public void spawnCreature(final Creature teraCreature) {
        this.creatureSpawns.add(teraCreature);
        teraCreature.getKnownList().update(true);
    }

    public void despawnCreature(final Creature teraCreature, final boolean mustRespawn) {
        this.creatureSpawns.remove(teraCreature);
        teraCreature.getKnownList().clear();
        teraCreature.getAggroList().clear();
        
        if (mustRespawn) {
            ThreadPoolService.getInstance().scheduleTask(new CreatureRespawnTask(teraCreature), SpawnConfig.SPAWN_CREATURE_RESPAWN_TIME, TimeUnit.SECONDS);
        }
    }

    public Creature getCreatureById(final int id) {
        for (final Creature teraCreature : this.creatureSpawns) {
            if (teraCreature.getId() == id) {
                return teraCreature;
            }
        }
        return null;
    }
    
    public Creature getCreatureByFullId(final int fullId) {
        for (final Creature teraCreature : this.creatureSpawns) {
            if (teraCreature.getTemplate().getFullId() == fullId) {
                return teraCreature;
            }
        }
        return null;
    }

    public List<Creature> getCreaturesByMapId(final int mapId) {
        final List<Creature> creatures = new FastList<>();
        for (final Creature teraCreature : this.creatureSpawns) {
            if (teraCreature == null || teraCreature.getCurrentStats().isDead()) {
                continue;
            }
            
            if (teraCreature.getWorldPosition().getMapId() == mapId) {
                creatures.add(teraCreature);
            }
        }

        return creatures;
    }

    /********************
     * GATHERS
     ********************/
    public void spawnGather(final Gather gather) {
        this.gatherSpawns.add(gather);
        gather.getKnownList().update(true);
    }

    public void despawnGather(final Gather gather, final boolean mustRespawn) {
        this.gatherSpawns.remove(gather);
        gather.getKnownList().clear();

        if (mustRespawn) {
            ThreadPoolService.getInstance().scheduleTask(new GatherRespawnTask(gather), SpawnConfig.SPAWN_GATHER_RESPAWN_TIME, TimeUnit.SECONDS);
        }
    }

    public List<Gather> getGathersByMapId(final int mapId) {
        final List<Gather> gathers = new FastList<>();
        for (final Gather gather : this.gatherSpawns) {
            if (gather.getWorldPosition().getMapId() == mapId && gather.getRemainingGather() != 0) {
                gathers.add(gather);
            }
        }
        return gathers;
    }

    /********************
     * CAMPFIRE
     ********************/
    public void spawnCampFire(final CampFire campFire, final boolean mustDespawn) {
        this.campFireSpawns.add(campFire);
        campFire.getKnownList().update(true);

        if (mustDespawn) {
            ThreadPoolService.getInstance().scheduleTask(new CampFireDespawnTask(campFire), SpawnConfig.SPAWN_CAMPFIRE_DESPAWN_TIME, TimeUnit.SECONDS);
        }
    }

    public void spawnCampFire(final CampFire campFire) {
        this.spawnCampFire(campFire, true);
    }

    public void despawnCampFire(final CampFire campFire) {
        this.campFireSpawns.remove(campFire);
        campFire.getKnownList().clear();
    }

    public List<CampFire> getCampFiresByMapId(final int mapId) {
        final List<CampFire> campFires = new FastList<>();
        for (final CampFire campFire : this.campFireSpawns) {
            if (campFire.getWorldPosition().getMapId() == mapId) {
                campFires.add(campFire);
            }
        }
        return campFires;
    }

    public CampFire getNearestCampFire(final WorldPosition worldPosition) {
        CampFire nearestCampFire = null;
        double nearestDistance = 0.0;
        for (final CampFire campFire : this.getCampFiresByMapId(worldPosition.getMapId())) {
            if (nearestCampFire == null || campFire.getWorldPosition().distanceTo(worldPosition) < nearestDistance) {
                nearestCampFire = campFire;
                nearestDistance = nearestCampFire.getWorldPosition().distanceTo(worldPosition);
            }
        }
        return nearestCampFire;
    }

    /********************
     * DROPITEM
     ********************/
    public void spawnDropItem(final DropItem dropItem) {
        this.dropItemSpawns.add(dropItem);
        dropItem.getKnownList().update(true);
    }

    public void despawnDropItem(final DropItem dropItem) {
        this.dropItemSpawns.remove(dropItem);
        dropItem.getKnownList().clear();
    }

    public List<DropItem> getDropItemsByMapId(final int mapId) {
        final List<DropItem> dropItems = new FastList<>();
        for (final DropItem dropItem : this.dropItemSpawns) {
            if (dropItem.getWorldPosition().getMapId() == mapId) {
                dropItems.add(dropItem);
            }
        }
        return dropItems;
    }
    
    /********************
     * GAMEOBJECT
     ********************/
    public void spawnGameObject(final GameObject gameObject) {
        this.gameObjectSpawns.add(gameObject);
        gameObject.getKnownList().update(true);
    }

    public void despawnGameObject(final GameObject gameObject) {
        this.gameObjectSpawns.remove(gameObject);
        gameObject.getKnownList().clear();
    }

    public List<GameObject> getGameObjectsByMapId(final int mapId) {
        final List<GameObject> gameObjects = new FastList<>();
        for (final GameObject gameObject : this.gameObjectSpawns) {
            if (gameObject.getWorldPosition().getMapId() == mapId) {
                gameObjects.add(gameObject);
            }
        }
        return gameObjects;
    }

    private synchronized final void clearDuplicate(final List<? extends VisibleTeraObject> collection) {
        final Set<WorldPosition> wps = new FastSet<>();
        final Iterator<? extends VisibleTeraObject> itr = collection.iterator();
        while (itr.hasNext()) {
            final VisibleTeraObject visibleTeraObject = itr.next();
            final WorldPosition worldPosition = visibleTeraObject.getWorldPosition();
            if (wps.contains(worldPosition)) {
                log.error("Spawn entity duplication : " + visibleTeraObject.getId());
                itr.remove();
                continue;
            }

            wps.add(worldPosition);
        }
    }

    /** SINGLETON */
    public static SpawnService getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        protected static final SpawnService instance = new SpawnService();
    }
}
