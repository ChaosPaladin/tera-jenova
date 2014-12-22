package com.angelis.tera.game.process.model.visible;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import javolution.util.FastList;
import javolution.util.FastSet;

import com.angelis.tera.game.process.model.TeraObject;
import com.angelis.tera.game.process.model.account.enums.DisplayRangeEnum;
import com.angelis.tera.game.process.model.campfire.CampFire;
import com.angelis.tera.game.process.model.creature.Monster;
import com.angelis.tera.game.process.model.creature.Npc;
import com.angelis.tera.game.process.model.creature.VisibleObjectEventTypeEnum;
import com.angelis.tera.game.process.model.drop.DropItem;
import com.angelis.tera.game.process.model.gameobject.GameObject;
import com.angelis.tera.game.process.model.gather.Gather;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.model.visible.enums.VisibleTypeEnum;
import com.angelis.tera.game.process.services.SpawnService;
import com.angelis.tera.game.process.services.WorldService;

public final class KnownList {

    private final VisibleTeraObject owner;

    private final Set<Player> players = Collections.synchronizedSet(new FastSet<Player>());
    private final Set<Npc> npcs = Collections.synchronizedSet(new FastSet<Npc>());
    private final Set<Monster> monsters = Collections.synchronizedSet(new FastSet<Monster>());
    private final Set<Gather> gathers = Collections.synchronizedSet(new FastSet<Gather>());
    private final Set<CampFire> campFires = Collections.synchronizedSet(new FastSet<CampFire>());
    private final Set<DropItem> dropItems = Collections.synchronizedSet(new FastSet<DropItem>());
    private final Set<GameObject> gameObjects = Collections.synchronizedSet(new FastSet<GameObject>());

    public KnownList(final VisibleTeraObject owner) {
        this.owner = owner;
    }

    public final Set<VisibleTeraObject> getVisible(final VisibleTypeEnum visibleType) {
        final Set<VisibleTeraObject> visible = new FastSet<>();
        switch (visibleType) {
            case ALL:
                visible.addAll(this.players);
                visible.addAll(this.npcs);
                visible.addAll(this.monsters);
                visible.addAll(this.gathers);
                visible.addAll(this.campFires);
                visible.addAll(this.dropItems);
                visible.addAll(this.gameObjects);
            break;

            case PLAYER:
                visible.addAll(this.players);
            break;
            
            case NPC:
                visible.addAll(this.npcs);
            break;

            case MONSTER:
                visible.addAll(this.monsters);
            break;
            
            case CREATURE:
                visible.addAll(this.npcs);
                visible.addAll(this.monsters);
            break;

            case GATHER:
                visible.addAll(this.gathers);
            break;

            case CAMPFIRE:
                visible.addAll(this.campFires);
            break;
            
            case DROP_ITEM:
                visible.addAll(this.dropItems);
            break;
            
            case GAME_OBJECT:
                visible.addAll(this.gameObjects);
            break;
        }

        return visible;
    }

    public final void addKnown(final TeraObject object) {
        if (object.equals(this.owner)) {
            return;
        }
        
        final boolean isNew = !this.isKnown(object);
        if (isNew) {
            final Class<? extends TeraObject> objectClass = object.getClass();

            if (Player.class.isAssignableFrom(objectClass)) {
                synchronized (this.players) {
                    this.players.add((Player) object);
                }
            }
            else if (Npc.class.isAssignableFrom(objectClass)) {
                synchronized (this.npcs) {
                    this.npcs.add((Npc) object);
                }
            }
            else if (Monster.class.isAssignableFrom(objectClass)) {
                synchronized (this.monsters) {
                    this.monsters.add((Monster) object);
                }
            }
            else if (Gather.class.isAssignableFrom(objectClass)) {
                synchronized (this.gathers) {
                    this.gathers.add((Gather) object);
                }
            }
            else if (CampFire.class.isAssignableFrom(objectClass)) {
                synchronized (this.campFires) {
                    this.campFires.add((CampFire) object);
                }
            }
            else if (DropItem.class.isAssignableFrom(objectClass)) {
                synchronized (this.dropItems) {
                    this.dropItems.add((DropItem) object);
                }
            }
            else if (GameObject.class.isAssignableFrom(objectClass)) {
                synchronized (this.gameObjects) {
                    this.gameObjects.add((GameObject) object);
                }
            }
            else {
                return;
            }

            owner.getController().update(VisibleObjectEventTypeEnum.APPEAR, object);
        }
    }

    public final void removeKnown(final TeraObject object) {
        if (object.equals(this.owner)) {
            return;
        }
        
        final boolean isOld = this.isKnown(object);
        if (isOld) {
            final Class<? extends TeraObject> objectClass = object.getClass();

            if (Player.class.isAssignableFrom(objectClass)) {
                synchronized (this.players) {
                    this.players.remove(object);
                }
            }
            else if (Npc.class.isAssignableFrom(objectClass)) {
                synchronized (this.npcs) {
                    this.npcs.remove(object);
                }
            }
            else if (Monster.class.isAssignableFrom(objectClass)) {
                synchronized (this.monsters) {
                    this.monsters.remove(object);
                }
            }
            else if (Gather.class.isAssignableFrom(objectClass)) {
                synchronized (this.gathers) {
                    this.gathers.remove(object);
                }
            }
            else if (CampFire.class.isAssignableFrom(objectClass)) {
                synchronized (this.campFires) {
                    this.campFires.remove(object);
                }
            }
            else if (DropItem.class.isAssignableFrom(objectClass)) {
                synchronized (this.dropItems) {
                    this.dropItems.remove(object);
                }
            }
            else if (GameObject.class.isAssignableFrom(objectClass)) {
                synchronized (this.gameObjects) {
                    this.gameObjects.remove(object);
                }
            }
            else {
                return;
            }

            owner.getController().update(VisibleObjectEventTypeEnum.DISAPPEAR, object);
        }
    }

    public void forceUpdate() {
        this.clear();
        this.update();
    }
    
    public void clear() {
        for (final VisibleTeraObject visibleTeraObject : this.getVisible(VisibleTypeEnum.ALL)) {
            visibleTeraObject.getKnownList().removeKnown(this.owner);
            this.removeKnown(visibleTeraObject);
        }
    }

    public void update() {
        new Thread() {
            @Override
            public void run() {
                final int mapId = KnownList.this.owner.getWorldPosition().getMapId();
                
                final List<VisibleTeraObject> visibleTeraObjectInMap = new FastList<>();
                visibleTeraObjectInMap.addAll(WorldService.getInstance().getPlayersByMap(mapId));
                visibleTeraObjectInMap.addAll(SpawnService.getInstance().getCreaturesByMapId(mapId));
                visibleTeraObjectInMap.addAll(SpawnService.getInstance().getGathersByMapId(mapId));
                visibleTeraObjectInMap.addAll(SpawnService.getInstance().getCampFiresByMapId(mapId));
                visibleTeraObjectInMap.addAll(SpawnService.getInstance().getDropItemsByMapId(mapId));
                visibleTeraObjectInMap.addAll(SpawnService.getInstance().getGameObjectsByMapId(mapId));

                for (final VisibleTeraObject visibleTeraObject : visibleTeraObjectInMap) {
                    if (visibleTeraObject.equals(KnownList.this.owner)) {
                        continue;
                    }

                    final boolean isVisible = KnownList.this.isVisible(visibleTeraObject);
                    final boolean isKnown = KnownList.this.isKnown(visibleTeraObject);

                    if (isVisible ^ isKnown) {
                        if (isVisible && !isKnown) {
                            KnownList.this.addKnown(visibleTeraObject);
                            visibleTeraObject.getKnownList().addKnown(KnownList.this.owner);
                        }
                        else if (!isVisible && isKnown) {
                            KnownList.this.removeKnown(visibleTeraObject);
                            visibleTeraObject.getKnownList().removeKnown(KnownList.this.owner);
                        }
                    }
                }
            }
        }.start();
    }
    
    public final void update(final boolean clear) {
        if (clear) {
            this.clear();
        }
        
        this.update();
    }

    public void notify(final VisibleTypeEnum visibleType, final VisibleObjectEventTypeEnum creatureEventType, final Object... data) {
        for (final VisibleTeraObject visibleTeraObject : this.getVisible(visibleType)) {
            visibleTeraObject.getController().update(creatureEventType, this.owner, data);
        }
    }

    private final boolean isKnown(final TeraObject object) {
        final Class<? extends TeraObject> objectClass = object.getClass();

        if (Player.class.isAssignableFrom(objectClass)) {
            synchronized (this.players) {
                return this.players.contains(object);
            }
        }
        else if (Npc.class.isAssignableFrom(objectClass)) {
            synchronized (this.npcs) {
                return this.npcs.contains(object);
            }
        }
        else if (Monster.class.isAssignableFrom(objectClass)) {
            synchronized (this.monsters) {
                return this.monsters.contains(object);
            }
        }
        else if (Gather.class.isAssignableFrom(objectClass)) {
            synchronized (this.gathers) {
                return this.gathers.contains(object);
            }
        }
        else if (CampFire.class.isAssignableFrom(objectClass)) {
            synchronized (this.campFires) {
                return this.campFires.contains(object);
            }
        }
        else if (DropItem.class.isAssignableFrom(objectClass)) {
            synchronized (this.dropItems) {
                return this.dropItems.contains(object);
            }
        }
        else if (GameObject.class.isAssignableFrom(objectClass)) {
            synchronized (this.gameObjects) {
                return this.gameObjects.contains(object);
            }
        }

        return false;
    }

    private final boolean isVisible(final VisibleTeraObject object) {
        return this.owner.getWorldPosition().distanceTo(object.getWorldPosition()) < DisplayRangeEnum.HIGH.value + 10;
    }
}
