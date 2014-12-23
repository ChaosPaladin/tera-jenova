package com.angelis.tera.game.process.model.enums;

import org.apache.log4j.Logger;

import com.angelis.tera.game.process.model.action.Action;
import com.angelis.tera.game.process.model.campfire.CampFire;
import com.angelis.tera.game.process.model.creature.Monster;
import com.angelis.tera.game.process.model.creature.Npc;
import com.angelis.tera.game.process.model.dialog.Dialog;
import com.angelis.tera.game.process.model.drop.DropItem;
import com.angelis.tera.game.process.model.gameobject.GameObject;
import com.angelis.tera.game.process.model.gather.Gather;
import com.angelis.tera.game.process.model.item.Item;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.model.player.request.AbstractRequest;
import com.angelis.tera.game.process.model.player.request.NpcTradeRequest;
import com.angelis.tera.game.process.model.player.request.PartyInviteRequest;
import com.angelis.tera.game.process.model.player.request.PegasusShowMapRequest;

public enum ObjectFamilyEnum {
    // Abstract
    ATTACK("0", Action.class),
    REQUEST("0", AbstractRequest.class, PartyInviteRequest.class, NpcTradeRequest.class, PegasusShowMapRequest.class),

    // Visible
    PLAYER("00800000", Player.class),
    CREATURE("00800C00", Npc.class, Monster.class),
    GATHER("00800400", Gather.class),
    GAME_OBJECT("00800A00", GameObject.class),
    DIALOG("00800C00", Dialog.class),
    DROP_ITEM("00800900", DropItem.class),
    CAMPFIRE("00801000", CampFire.class),
    INVENTORY_ITEM("0", Item.class),

    SYSTEM("0", Void.class), // TODO
    PROJECTILE("0", Void.class), // TODO
    GUILD("0", Void.class); // TODO

    public final Class<?>[] associatedClass;
    public final String value;

    ObjectFamilyEnum(final String value, final Class<?>... associatedClass) {
        this.value = value;
        this.associatedClass = associatedClass;
    }

    public static final ObjectFamilyEnum fromClass(final Class<?> clazz) {
        for (final ObjectFamilyEnum objectFamily : ObjectFamilyEnum.values()) {
            for (final Class<?> associatedClass : objectFamily.associatedClass) {
                if (clazz == associatedClass) {
                    return objectFamily;
                }
            }
        }

        Logger.getLogger(ObjectFamilyEnum.class.getName()).error("Can't find ObjectFamilyEnum with class " + clazz.getName());
        return null;
    }

    public static final ObjectFamilyEnum fromValue(final String value) {
        for (final ObjectFamilyEnum objectFamily : ObjectFamilyEnum.values()) {
            if (value.equals(objectFamily.value)) {
                return objectFamily;
            }
        }

        Logger.getLogger(ObjectFamilyEnum.class.getName()).error("Can't find ObjectFamilyEnum with value " + value);
        return null;
    }
}
