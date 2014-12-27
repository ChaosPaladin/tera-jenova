package com.angelis.tera.game.process.tasks.creature;

import com.angelis.tera.game.config.PlayerConfig;
import com.angelis.tera.game.presentation.network.packet.server.player.SM_PLAYER_STATS_UPDATE;
import com.angelis.tera.game.process.model.abnormality.Abnormality;
import com.angelis.tera.game.process.model.campfire.CampFire;
import com.angelis.tera.game.process.model.creature.AbstractCreature;
import com.angelis.tera.game.process.model.creature.BaseStats;
import com.angelis.tera.game.process.model.creature.CurrentStats;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.model.player.enums.PlayerModeEnum;
import com.angelis.tera.game.process.model.template.Template;
import com.angelis.tera.game.process.tasks.AbstractTask;
import com.angelis.tera.game.process.tasks.TaskTypeEnum;

public class CreatureStatsModifierTask extends AbstractTask<AbstractCreature> {

    private short tick;

    public CreatureStatsModifierTask(final AbstractCreature linkedObject) {
        super(linkedObject, TaskTypeEnum.PLAYER_STATS_MODIFIER);
    }

    @Override
    public void execute() {
        tick++;
        if (tick > 1000) {
            tick = 1;
        }

        final CurrentStats currentStats = this.linkedObject.getCurrentStats();
        if (currentStats.isDead()) {
            return;
        }
        
        for (final Abnormality abnormality : this.linkedObject.getAbnormalities()) {
            if (abnormality.getDelay() % tick == 0) {
                currentStats.addHp(abnormality.getHpModifier());
                currentStats.addMp(abnormality.getMpModifier());
            }
        }

        if (this.linkedObject instanceof Player) {
            final Player player = (Player) this.linkedObject;
            if (!player.isOnline()) {
                this.cancel();
                return;
            }

            if (PlayerConfig.PLAYER_GAIN_GATHERCRAFTPOINT_DELAY % tick == 0) {
                player.addGatherCraftPoints(PlayerConfig.PLAYER_GAIN_GATHERCRAFTPOINT_AMOUNT);
            }
            
            final CampFire activeCampFire = player.getActiveCampFire();
            if (activeCampFire != null) {
                if (PlayerConfig.PLAYER_GAIN_STAMINA_DELAY % tick == 0) {
                    currentStats.addStamina(activeCampFire.getRate());
                }
            }

            final Template template = this.linkedObject.getTemplate();
            final BaseStats baseStats = template.getBaseStats();
            if (player.getPlayerMode() == PlayerModeEnum.ARMORED) {
                currentStats.addHp(baseStats.getCombatHpRegen());
                currentStats.addMp(baseStats.getCombatMpRegen());
            }
            else {
                currentStats.addMp(baseStats.getNaturalMpRegen());
                currentStats.removeMp(baseStats.getNaturalMpDegen());
                if (currentStats.getRe() > 0) {
                    currentStats.addRe(-10);
                }
            }
            player.getConnection().sendPacket(new SM_PLAYER_STATS_UPDATE(player));
        }
    }
}
