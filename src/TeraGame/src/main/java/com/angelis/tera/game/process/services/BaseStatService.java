package com.angelis.tera.game.process.services;

import java.util.Map;
import java.util.Set;

import javolution.util.FastMap;

import org.apache.log4j.Logger;

import com.angelis.tera.common.domain.mapper.MapperManager;
import com.angelis.tera.common.services.AbstractService;
import com.angelis.tera.game.domain.entity.xml.BaseStatsEntity;
import com.angelis.tera.game.domain.entity.xml.players.PlayerBaseStatsEntity;
import com.angelis.tera.game.domain.entity.xml.players.PlayerBaseStatsEntityHolder;
import com.angelis.tera.game.domain.mapper.xml.BaseStatsMapper;
import com.angelis.tera.game.process.model.creature.AbstractCreature;
import com.angelis.tera.game.process.model.creature.BaseStats;
import com.angelis.tera.game.process.model.creature.CreatureBonusStats;
import com.angelis.tera.game.process.model.creature.CurrentStats;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.model.player.enums.PlayerClassEnum;
import com.angelis.tera.game.process.model.template.Template;
import com.angelis.tera.game.process.model.visible.VisibleTeraObject;

public class BaseStatService extends AbstractService {

    /** LOGGER */
    private static Logger log = Logger.getLogger(BaseStatService.class.getName());
    
    private final Map<PlayerClassEnum, Map<Integer, BaseStats>> playerClassBaseStats = new FastMap<>();

    @Override
    public void onInit() {
        // PLAYER BASES STATS
        final BaseStatsMapper baseStatsMapper = MapperManager.getXMLMapper(BaseStatsMapper.class);
        final Set<PlayerBaseStatsEntity> playerBaseStatEntities = XMLService.getInstance().getEntity(PlayerBaseStatsEntityHolder.class).getPlayerBaseStats();
        for (final PlayerBaseStatsEntity playerBaseStatsEntity : playerBaseStatEntities) {
            final Map<Integer, BaseStats> playerBasesStats = new FastMap<>();
            for (final BaseStatsEntity baseStatsEntity : playerBaseStatsEntity.getBaseStats()) {
                playerBasesStats.put(baseStatsEntity.getLevel(), baseStatsMapper.map(baseStatsEntity));
            }
            playerClassBaseStats.put(playerBaseStatsEntity.getTargetClass(), playerBasesStats);
        }
        log.info("BaseStatService started");
    }

    @Override
    public void onDestroy() {
        log.info("BaseStatService started");
    }

    public void onPlayerCreate(final Player player) {
        affectVisibleObjectBaseStats(player, getPlayerBaseStatByLevel(player.getPlayerClass(), player.getLevel()));
        affectCreatureCurrentStats(player);
        affectCreatureBonusStats(player);
    }

    public void onPlayerConnect(final Player player) {
        affectVisibleObjectBaseStats(player, getPlayerBaseStatByLevel(player.getPlayerClass(), player.getLevel()));
        affectCreatureCurrentStats(player);
        affectCreatureBonusStats(player);
    }

    public void onPlayerLevelUp(final Player player) {
        affectVisibleObjectBaseStats(player, getPlayerBaseStatByLevel(player.getPlayerClass(), player.getLevel()));
        affectCreatureCurrentStats(player);
        affectCreatureBonusStats(player);
    }

    public BaseStats getPlayerBaseStatByLevel(final PlayerClassEnum playerClass, final int level) {
        final Map<Integer, BaseStats> classBaseStats = playerClassBaseStats.get(playerClass);
        if (classBaseStats == null) {
            return null;
        }
        
        return classBaseStats.get(level);
    }

    public final void affectVisibleObjectBaseStats(final VisibleTeraObject creature, final BaseStats baseStats) {
        final Template template = creature.getTemplate();
        BaseStats creatureBaseStats = template.getBaseStats();
        if (creatureBaseStats == null) {
            creatureBaseStats = new BaseStats();
            template.setBaseStats(creatureBaseStats);
        }

        if (baseStats == null) {
            return;
        }

        creatureBaseStats.setBaseHp(baseStats.getBaseHp());
        creatureBaseStats.setBaseMp(baseStats.getBaseMp());
        if (creature instanceof Player) {
            if (((Player) creature).getPlayerClass() == PlayerClassEnum.WARRIOR) {
                creatureBaseStats.setBaseRe(100);
            }
        }

        creatureBaseStats.setAttack(baseStats.getAttack());
        creatureBaseStats.setDefense(baseStats.getDefense());
        creatureBaseStats.setImpact(baseStats.getImpact());
        creatureBaseStats.setBalance(baseStats.getBalance());
        creatureBaseStats.setCritRate(baseStats.getCritRate());
        creatureBaseStats.setCritResistance(baseStats.getCritResistance());
        creatureBaseStats.setCritPower(baseStats.getCritPower());
        creatureBaseStats.setPower(baseStats.getPower());
        creatureBaseStats.setEndurance(baseStats.getEndurance());
        creatureBaseStats.setImpactFactor(baseStats.getImpactFactor());
        creatureBaseStats.setBalanceFactor(baseStats.getBalanceFactor());
        creatureBaseStats.setAttackSpeed(baseStats.getAttackSpeed());
        creatureBaseStats.setSpeed(baseStats.getSpeed());
        creatureBaseStats.setWeakeningResistance(baseStats.getWeakeningResistance());
        creatureBaseStats.setPeriodicResistance(baseStats.getPeriodicResistance());
        creatureBaseStats.setStunResistance(baseStats.getStunResistance());
        creatureBaseStats.setNaturalMpRegen(baseStats.getNaturalMpRegen());
        creatureBaseStats.setNaturalMpDegen(baseStats.getNaturalMpDegen());
        creatureBaseStats.setCombatHpRegen(baseStats.getCombatHpRegen());
        creatureBaseStats.setCombatMpRegen(baseStats.getCombatMpRegen());
    }

    public final void affectCreatureBonusStats(final AbstractCreature creature) {
        CreatureBonusStats creatureBonusStats = creature.getCreatureBonusStats();
        if (creatureBonusStats == null) {
            creatureBonusStats = new CreatureBonusStats();
            creature.setCreatureBonusStats(creatureBonusStats);
        }

        if (creature instanceof Player) {
            final Player player = (Player) creature;
            creatureBonusStats.setSpeed(0);
            if (player.getActiveMount() != null) {
                final int delta = player.getActiveMount().getSpeed() - creature.getCurrentStats().getSpeed();
                creatureBonusStats.setSpeed(delta);
            }
        }
    }

    public void affectCreatureCurrentStats(final AbstractCreature creature) {
        CurrentStats currentStats = creature.getCurrentStats();
        if (currentStats == null) {
            currentStats = new CurrentStats();
            currentStats.setStamina(120);
            creature.setCurrentStats(currentStats);
        }

        final Template template = creature.getTemplate();
        final BaseStats creatureBaseStats = template.getBaseStats();
        currentStats.setHp(creatureBaseStats.getBaseHp());
        currentStats.setMp(creatureBaseStats.getBaseMp());
        currentStats.setSpeed(creatureBaseStats.getSpeed());
    }

    /** SINGLETON */
    public static BaseStatService getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        protected static final BaseStatService instance = new BaseStatService();
    }
}
