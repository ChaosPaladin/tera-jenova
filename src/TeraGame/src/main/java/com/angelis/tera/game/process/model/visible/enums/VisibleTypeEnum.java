package com.angelis.tera.game.process.model.visible.enums;

import com.angelis.tera.game.process.model.campfire.CampFire;
import com.angelis.tera.game.process.model.creature.AbstractCreature;
import com.angelis.tera.game.process.model.creature.Monster;
import com.angelis.tera.game.process.model.creature.Npc;
import com.angelis.tera.game.process.model.drop.DropItem;
import com.angelis.tera.game.process.model.gameobject.GameObject;
import com.angelis.tera.game.process.model.gather.Gather;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.model.visible.VisibleTeraObject;

public enum VisibleTypeEnum {
    ALL(VisibleTeraObject.class),
    PLAYER(Player.class),
    NPC(Npc.class),
    MONSTER(Monster.class),
    CREATURE(AbstractCreature.class),
    GATHER(Gather.class),
    CAMPFIRE(CampFire.class),
    DROP_ITEM(DropItem.class),
    GAME_OBJECT(GameObject.class),
    ;
    
    public final Class<? extends VisibleTeraObject> associatedClass;

    private VisibleTypeEnum(final Class<? extends VisibleTeraObject> associatedClass) {
        this.associatedClass = associatedClass;
    }
}
