package com.angelis.tera.game.process.controllers;

import java.util.EnumSet;
import java.util.List;

import javolution.util.FastList;

import com.angelis.tera.game.presentation.network.SystemMessages;
import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;
import com.angelis.tera.game.presentation.network.packet.server.SM_CAMPFIRE_DESPAWN;
import com.angelis.tera.game.presentation.network.packet.server.SM_CAMPFIRE_SPAWN;
import com.angelis.tera.game.presentation.network.packet.server.SM_CREATURE_DESPAWN;
import com.angelis.tera.game.presentation.network.packet.server.SM_CREATURE_HP_UPDATE;
import com.angelis.tera.game.presentation.network.packet.server.SM_CREATURE_MOVE;
import com.angelis.tera.game.presentation.network.packet.server.SM_CREATURE_SHOW_HP;
import com.angelis.tera.game.presentation.network.packet.server.SM_CREATURE_SPAWN;
import com.angelis.tera.game.presentation.network.packet.server.SM_DROP_ITEM_DESPAWN;
import com.angelis.tera.game.presentation.network.packet.server.SM_DROP_ITEM_SPAWN;
import com.angelis.tera.game.presentation.network.packet.server.SM_GAMEOBJECT_DESPAWN;
import com.angelis.tera.game.presentation.network.packet.server.SM_GAMEOBJECT_SPAWN;
import com.angelis.tera.game.presentation.network.packet.server.SM_GATHER_DESPAWN;
import com.angelis.tera.game.presentation.network.packet.server.SM_GATHER_SPAWN;
import com.angelis.tera.game.presentation.network.packet.server.SM_PLAYER_DESPAWN;
import com.angelis.tera.game.presentation.network.packet.server.SM_PLAYER_MOUNT;
import com.angelis.tera.game.presentation.network.packet.server.SM_PLAYER_MOVE;
import com.angelis.tera.game.presentation.network.packet.server.SM_PLAYER_SPAWN;
import com.angelis.tera.game.presentation.network.packet.server.SM_PLAYER_STATE;
import com.angelis.tera.game.presentation.network.packet.server.SM_PLAYER_UNK1;
import com.angelis.tera.game.presentation.network.packet.server.SM_PLAYER_UNK2;
import com.angelis.tera.game.process.controllers.enums.RightEnum;
import com.angelis.tera.game.process.model.TeraObject;
import com.angelis.tera.game.process.model.campfire.CampFire;
import com.angelis.tera.game.process.model.chainedaction.AbstractChainedAction;
import com.angelis.tera.game.process.model.creature.AbstractCreature;
import com.angelis.tera.game.process.model.creature.Creature;
import com.angelis.tera.game.process.model.creature.VisibleObjectEventTypeEnum;
import com.angelis.tera.game.process.model.dialog.Dialog;
import com.angelis.tera.game.process.model.drop.DropItem;
import com.angelis.tera.game.process.model.enums.DespawnTypeEnum;
import com.angelis.tera.game.process.model.gameobject.GameObject;
import com.angelis.tera.game.process.model.gather.Gather;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.model.player.enums.PlayerModeEnum;
import com.angelis.tera.game.process.model.player.enums.PlayerMoveTypeEnum;
import com.angelis.tera.game.process.model.player.enums.PlayerRelationEnum;
import com.angelis.tera.game.process.model.player.request.AbstractRequest;
import com.angelis.tera.game.process.model.visible.VisibleTeraObject;
import com.angelis.tera.game.process.services.PlayerService;
import com.angelis.tera.game.process.services.VisibleService;
import com.angelis.tera.game.utils.QuestUtils;

public class PlayerController extends Controller<Player> {

    private final EnumSet<RightEnum> rights = EnumSet.of(RightEnum.WALK, RightEnum.TALK);

    private AbstractRequest<?, ?> request;
    private Dialog dialog;
    private AbstractChainedAction chainedAction;
    private boolean isInBattle;

    public boolean can(final RightEnum right) {
        return this.rights.contains(right);
    }

    public final void removeRight(final RightEnum right) {
        this.rights.remove(right);
    }

    public final void addRight(final RightEnum right) {
        this.rights.add(right);
    }

    public AbstractRequest<?, ?> getRequest() {
        return request;
    }

    public void setRequest(final AbstractRequest<?, ?> request) {
        this.request = request;
    }

    public Dialog getDialog() {
        return dialog;
    }

    public void setDialog(final Dialog dialog) {
        this.dialog = dialog;
    }

    public AbstractChainedAction getChainedAction() {
        return chainedAction;
    }

    public void setChainedAction(final AbstractChainedAction chainedAction) {
        this.chainedAction = chainedAction;
    }

    @Override
    public void update(final VisibleObjectEventTypeEnum creatureEventType, final TeraObject object, final Object... data) {
        final Player player = this.owner;
        if (player == null || player.getConnection() == null) {
            return;
        }

        final List<TeraServerPacket> packets = new FastList<>();
        switch (creatureEventType) {
            case APPEAR:
                if (object instanceof Player) {
                    final Player oPlayer = (Player) object;
                    
                    // TODO player relation
                    PlayerRelationEnum playerRelation = PlayerRelationEnum.FRIENDLY;
                    if (player.getGroup() != null && oPlayer.getGroup() != null) {
                        if (player.getGroup().equals(oPlayer.getGroup())) {
                            playerRelation = PlayerRelationEnum.PARTY_MEMBER;
                        }
                    }
                    
                    if (player.getGuild() != null && oPlayer.getGuild() != null) {
                        if (player.getGuild().equals(oPlayer.getGuild())) {
                            playerRelation = PlayerRelationEnum.GUILD_MEMBER;
                        }
                    }
                    
                    if (player.getActiveDuelPlayer() != null && player.getActiveDuelPlayer().equals(oPlayer)) {
                        playerRelation = PlayerRelationEnum.DUEL_ENEMY;
                    }
                    packets.add(new SM_PLAYER_SPAWN(oPlayer, playerRelation));
                    packets.add(new SM_PLAYER_UNK1(oPlayer));
                    packets.add(new SM_PLAYER_UNK1(oPlayer));
                    packets.add(new SM_PLAYER_UNK2(oPlayer));

                    if (oPlayer.getActiveMount() != null)  {
                        packets.add(new SM_PLAYER_MOUNT(oPlayer, oPlayer.getActiveMount()));
                    }
                }
                else if (object instanceof Creature) {
                    final Creature teraCreature = (Creature) object;
                    packets.add(new SM_CREATURE_SPAWN(teraCreature));
                    QuestUtils.updatePlayerQuestNpcVillageInfo(this.owner, packets, teraCreature);
                }
                else if (object instanceof Gather) {
                    final Gather gather = (Gather) object;
                    packets.add(new SM_GATHER_SPAWN(gather));
                }
                else if (object instanceof CampFire) {
                    packets.add(new SM_CAMPFIRE_SPAWN((CampFire) object));
                }
                else if (object instanceof DropItem) {
                    packets.add(new SM_DROP_ITEM_SPAWN((DropItem) object));
                }
                else if (object instanceof GameObject) {
                    packets.add(new SM_GAMEOBJECT_SPAWN((GameObject) object));
                }
                else {
                    System.err.println("UNKNOW APPEAR OBJECT");
                }
            break;

            case DISAPPEAR:
                if (object instanceof Player) {
                    packets.add(new SM_PLAYER_DESPAWN((Player) object));
                }
                else if (object instanceof Creature) {
                    final Creature teraCreature = (Creature) object;
                    if (teraCreature.getCurrentStats().isDead()) {
                        packets.add(new SM_CREATURE_DESPAWN(teraCreature, DespawnTypeEnum.DEATH));
                    }
                    else {
                        packets.add(new SM_CREATURE_DESPAWN(teraCreature, DespawnTypeEnum.DELETE));
                    }
                }
                else if (object instanceof Gather) {
                    final Gather gather = (Gather) object;
                    if (gather.getRemainingGather() == 0) {
                        packets.add(new SM_GATHER_DESPAWN(gather, DespawnTypeEnum.GATHERED));
                    }
                    else {
                        packets.add(new SM_GATHER_DESPAWN(gather, DespawnTypeEnum.DELETE));
                    }
                }
                else if (object instanceof CampFire) {
                    packets.add(new SM_CAMPFIRE_DESPAWN((CampFire) object));
                }
                else if (object instanceof DropItem) {
                    packets.add(new SM_DROP_ITEM_DESPAWN((DropItem) object));
                }
                else if (object instanceof GameObject) {
                    packets.add(new SM_GAMEOBJECT_DESPAWN((GameObject) object));
                }
                else {
                    System.err.println("UNKNOW DISAPPEAR OBJECT");
                }
            break;

            case CREATURE_MOVE:
                final float x1 = (float) data[0];
                final float y1 = (float) data[1];
                final float z1 = (float) data[2];
                final short heading = (short) data[3];
                final float x2 = (float) data[4];
                final float y2 = (float) data[5];
                final float z2 = (float) data[6];

                if (player.getWorldPosition().distanceTo(x1, y1, z1) > player.getAccount().getOptions().getDisplayRange().value) {
                    // Creature is no more visible so it's a remove
                    if (object instanceof Player) {
                        packets.add(new SM_PLAYER_DESPAWN((Player) object));
                    }
                    else if (object instanceof Creature) {
                        packets.add(new SM_CREATURE_DESPAWN((Creature) object, DespawnTypeEnum.DELETE));
                    }
                }
                else {
                    // Creature is still visible so it's a simple move
                    if (object instanceof Player) {
                        final PlayerMoveTypeEnum playerMoveType = (PlayerMoveTypeEnum) data[7];
                        final byte[] unk2 = (byte[]) data[8];
                        final int unk3 = (int) data[9];

                        packets.add(new SM_PLAYER_MOVE((Player) object, x1, y1, z1, heading, x2, y2, z2, playerMoveType, unk2, unk3));
                    }
                    else if (object instanceof Creature) {
                        packets.add(new SM_CREATURE_MOVE((Creature) object, x1, y1, z1));
                    }
                }
            break;

            default:
        }

        for (final TeraServerPacket packet : packets) {
            player.getConnection().sendPacket(packet);
        }
    }

    @Override
    public void onDie(final VisibleTeraObject killer) {
        PlayerService.getInstance().onPlayerDie(this.owner);
    }

    @Override
    public void onRespawn() {
        // TODO Auto-generated method stub
    }

    @Override
    public void onStartAttack(final VisibleTeraObject target) {
        if (!isInBattle) {
            this.owner.setPlayerMode(PlayerModeEnum.ARMORED);

            final List<TeraServerPacket> packets = new FastList<>();
            packets.add(new SM_PLAYER_STATE(this.owner));
            packets.add(SystemMessages.BATTLE_START());
            VisibleService.getInstance().sendPacketsForVisible(this.owner, packets);
            this.isInBattle = true;
        }
    }

    @Override
    public void onDamage(final VisibleTeraObject attacker, final int damage) {
        this.owner.getCurrentStats().addHp(-damage);

        final List<TeraServerPacket> packets = new FastList<>();
        packets.add(new SM_CREATURE_HP_UPDATE(this.owner, (AbstractCreature) attacker, -damage));
        packets.add(new SM_CREATURE_SHOW_HP(this.owner, false));
        VisibleService.getInstance().sendPacketsForVisible(this.owner, packets);
    }

    @Override
    public void onEndAttack() {
        if (this.isInBattle) {
            this.owner.setPlayerMode(PlayerModeEnum.NORMAL);
            this.owner.getConnection().sendPacket(SystemMessages.BATTLE_END());
            VisibleService.getInstance().sendPacketForVisible(this.owner, new SM_PLAYER_STATE(this.owner));
            this.isInBattle = false;
        }
    }
}
