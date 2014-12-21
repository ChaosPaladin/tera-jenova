package com.angelis.tera.game.services;

import java.util.List;
import java.util.Set;

import javolution.util.FastList;

import org.apache.log4j.Logger;

import com.angelis.tera.common.services.AbstractService;
import com.angelis.tera.game.network.SystemMessages;
import com.angelis.tera.game.network.packet.TeraServerPacket;
import com.angelis.tera.game.network.packet.server.SM_CREATURE_TARGET_PLAYER;
import com.angelis.tera.game.process.model.action.Action;
import com.angelis.tera.game.process.model.attack.enums.AttackTypeEnum;
import com.angelis.tera.game.process.model.creature.AbstractCreature;
import com.angelis.tera.game.process.model.creature.Creature;
import com.angelis.tera.game.process.model.creature.CurrentStats;
import com.angelis.tera.game.process.model.enums.StorageTypeEnum;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.model.player.enums.PlayerModeEnum;
import com.angelis.tera.game.process.model.skill.SkillArgs;
import com.angelis.tera.game.process.model.storage.enums.InventorySlotEnum;
import com.angelis.tera.game.process.model.visible.VisibleTeraObject;
import com.angelis.tera.game.process.model.visible.enums.VisibleTypeEnum;

public class BattleService extends AbstractService {

    /** LOGGER */
    private static Logger log = Logger.getLogger(BattleService.class.getName());

    private BattleService() {
    }

    @Override
    public void onInit() {
        log.info("BattleService started");
    }

    @Override
    public void onDestroy() {
        log.info("BattleService stopped");
    }

    public void onPlayerAttack(final Player player, final SkillArgs skillArgs) {
        if (player.getStorage(StorageTypeEnum.INVENTORY).getStorageItemByInventorySlot(InventorySlotEnum.WEAPON) == null) {
            player.getConnection().sendPacket(SystemMessages.SKILL_WEAPON_IS_REQUIRED());
            return;
        }
        if (player.getActiveMount() != null) {
            player.getConnection().sendPacket(SystemMessages.YOU_CANNOT_USE_THAT_SKILL_AT_THE_MOMENT());
            return;
        }

        PlayerService.getInstance().onPlayerAttack(player, skillArgs);

        player.getWorldPosition().setPoint3D(skillArgs.getStartPosition().getPoint3D());

        final List<TeraServerPacket> packets = new FastList<>();

        final Action action = new Action();
        action.setAttackType(AttackTypeEnum.NORMAL);
        action.setSkillId(skillArgs.getSkillId());
        action.setStartPosition(skillArgs.getStartPosition());
        action.setEndPosition(skillArgs.getEndPosition());

        ActionService.getInstance().onActionStart(player, action);
        for (final VisibleTeraObject observer : this.getTargetForSkill(player, skillArgs)) {
            if (observer.getWorldPosition().distanceTo(player.getWorldPosition()) < 100) {
                final Creature creature = (Creature) observer;
                final CurrentStats currentStats = creature.getCurrentStats();
                if (currentStats.isDead()) {
                    continue;
                }

                player.getController().onStartAttack(creature);
                creature.getController().onStartAttack(player);

                final int damage = this.calculateDamage(player, creature, 1000);
                creature.getController().onDamage(player, damage);

                action.setTarget(creature);
                ActionService.getInstance().onActionResults(player, action);

                if (currentStats.isDead()) {
                    CreatureService.getInstance().killCreature(creature, player);
                    checkBattleState(player);
                }

                packets.add(new SM_CREATURE_TARGET_PLAYER(creature));
            }
        }
        VisibleService.getInstance().sendPacketsForVisible(player, packets);

        try {
            Thread.sleep(1500);
        }
        catch (final InterruptedException e) {
            e.printStackTrace();
        }

        ActionService.getInstance().onActionEnd(player, action);
    }

    private Set<VisibleTeraObject> getTargetForSkill(final Player player, final SkillArgs skillArgs) {
        final Set<VisibleTeraObject> visibleTeraObjects = player.getKnownList().getVisible(VisibleTypeEnum.MONSTER);
        // TODO
        return visibleTeraObjects;
    }

    public final void onPlayerMove(final Player player) {
        if (player.getPlayerMode() == PlayerModeEnum.ARMORED) {
            checkBattleState(player);
        }
    }

    public void onPlayerAttackRelease(final Player player, final Integer skillUid, final int type) {
        this.checkBattleState(player);
    }

    public List<Creature> getCreaturesAggroListContainingVisibleTeraObject(final VisibleTeraObject visibleTeraObject) {
        final List<Creature> teraCreatures = new FastList<>();
        for (final Creature teraCreature : SpawnService.getInstance().getCreaturesByMapId(visibleTeraObject.getWorldPosition().getMapId())) {
            if (teraCreature.getAggroList().isInAggroList(visibleTeraObject)) {
                teraCreatures.add(teraCreature);
            }
        }
        return teraCreatures;
    }

    private final int calculateDamage(final AbstractCreature creature, final AbstractCreature target, final float skillAtk) {
        if (skillAtk <= 0.0) {
            return 0;
        }

        float atk = 1.0f;
        if (creature instanceof Player) {
            atk = ((Player) creature).getTemplate().getBaseStats().getAttack();
        }

        /*
         * Player player = creature as Player; if (player != null) { atk =
         * player.Attack.Args.IsItemSkill ? 1 : player.GameStats.Attack; }
         */

        final int targetDefense = target.getTemplate().getBaseStats().getDefense();
        if (targetDefense <= 0) {
            return (int) (atk * skillAtk);
        }

        return (int) (atk * skillAtk / targetDefense);
    }

    private void checkBattleState(final Player player) {
        final List<Creature> teraCreatures = BattleService.getInstance().getCreaturesAggroListContainingVisibleTeraObject(player);
        if (teraCreatures.isEmpty()) {
            player.getController().onEndAttack();
        }
    }

    /** SINGLETON */
    public static BattleService getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        protected static final BattleService instance = new BattleService();
    }
}
