package com.angelis.tera.game.domain.mapper.database;

import java.util.List;
import java.util.Set;

import javolution.util.FastList;
import javolution.util.FastSet;

import com.angelis.tera.common.domain.entity.database.AbstractDatabaseEntity;
import com.angelis.tera.common.domain.mapper.MapperManager;
import com.angelis.tera.common.domain.mapper.database.AbstractDatabaseMapper;
import com.angelis.tera.common.process.model.AbstractModel;
import com.angelis.tera.common.utils.BeanUtils;
import com.angelis.tera.common.utils.CollectionUtils;
import com.angelis.tera.game.domain.entity.database.AccountEntity;
import com.angelis.tera.game.domain.entity.database.AchievementEntity;
import com.angelis.tera.game.domain.entity.database.CraftEntity;
import com.angelis.tera.game.domain.entity.database.GatherEntity;
import com.angelis.tera.game.domain.entity.database.PlayerEntity;
import com.angelis.tera.game.domain.entity.database.SkillEntity;
import com.angelis.tera.game.domain.entity.database.StorageEntity;
import com.angelis.tera.game.domain.entity.database.ZoneEntity;
import com.angelis.tera.game.process.model.Zone;
import com.angelis.tera.game.process.model.account.Account;
import com.angelis.tera.game.process.model.creature.CurrentStats;
import com.angelis.tera.game.process.model.player.Achievement;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.model.player.PlayerAppearance;
import com.angelis.tera.game.process.model.player.SkillList;
import com.angelis.tera.game.process.model.player.craft.Craft;
import com.angelis.tera.game.process.model.player.craft.CraftStats;
import com.angelis.tera.game.process.model.player.gather.Gather;
import com.angelis.tera.game.process.model.player.gather.GatherStats;
import com.angelis.tera.game.process.model.player.quest.QuestList;
import com.angelis.tera.game.process.model.skill.Skill;
import com.angelis.tera.game.process.model.storage.Storage;
import com.angelis.tera.game.process.model.visible.WorldPosition;

public class PlayerMapper extends AbstractDatabaseMapper<PlayerEntity, Player> {

    // MODEL -> ENTITY
    @Override
    protected PlayerEntity createNewEmptyEntity() {
        return new PlayerEntity();
    }

    @Override
    public void map(final Player model, final PlayerEntity playerEntity) {
        // DIRECT
        playerEntity.setName(model.getName());
        playerEntity.setGender(model.getGender());
        playerEntity.setRace(model.getRace());
        playerEntity.setPlayerClass(model.getPlayerClass());
        playerEntity.setLevel(model.getLevel());
        playerEntity.setExperience(model.getExperience());
        playerEntity.setCurrentRestedExperience(model.getCurrentRestedExperience());
        playerEntity.setMaxRestedExperience(model.getMaxRestedExperience());
        playerEntity.setCreationTime(model.getCreationTime());
        playerEntity.setDeletionTime(model.getDeletionTime());
        playerEntity.setLastOnlineTime(model.getLastOnlineTime());
        playerEntity.setOnline(model.isOnline());
        playerEntity.setDescription(model.getDescription());
        playerEntity.setUserSettings(model.getUserSettings());
        playerEntity.setTitle(model.getTitle());
        playerEntity.setPlayerMode(model.getPlayerMode());
        playerEntity.setCurrentZoneData(model.getCurrentZoneData());

        // CREATURE STATS
        final CurrentStats currentStats = model.getCurrentStats();
        playerEntity.setHp(currentStats.getHp());
        playerEntity.setMp(currentStats.getMp());
        playerEntity.setStamina(currentStats.getStamina());

        // WORLD POSITION
        final WorldPosition worldPosition = model.getWorldPosition();
        playerEntity.setMapId(worldPosition.getMapId());
        playerEntity.setX(worldPosition.getX());
        playerEntity.setY(worldPosition.getY());
        playerEntity.setZ(worldPosition.getZ());
        playerEntity.setHeading(worldPosition.getHeading());

        // PLAYER APPEARANCE
        final PlayerAppearance playerAppearance = model.getPlayerAppearance();
        playerEntity.setData(playerAppearance.getData());
        playerEntity.setDetails1(playerAppearance.getDetails1());
        playerEntity.setDetails2(playerAppearance.getDetails2());
    }

    @Override
    protected void finalizeDependencies(final Player model, final PlayerEntity entity, final List<Class<? extends AbstractModel>> excludedDependencies) {
        final List<Class<? extends AbstractModel>> dependenciesToExclude = CollectionUtils.createListFrom(Player.class);

        // ACCOUNT
        if (!excludedDependencies.contains(Account.class)) {
            final AccountMapper accountMapper = MapperManager.getDatabaseMapper(AccountMapper.class);
            entity.setAccount(accountMapper.map(model.getAccount(), dependenciesToExclude));
        }

        // CRAFT
        if (!excludedDependencies.contains(Craft.class)) {
            final CraftMapper craftMapper = MapperManager.getDatabaseMapper(CraftMapper.class);
            final Set<CraftEntity> craftEntities = new FastSet<>();
            for (final Craft craft : model.getCraftStats().getCrafts()) {
                final CraftEntity craftEntity = craftMapper.map(craft);
                craftEntity.setPlayer(entity);
                craftEntities.add(craftEntity);
            }
            entity.setCrafts(craftEntities);
        }

        // STORAGE
        if (!excludedDependencies.contains(Storage.class)) {
            final StorageMapper storageMapper = MapperManager.getDatabaseMapper(StorageMapper.class);
            final Set<StorageEntity> storageEntities = new FastSet<>();
            for (final Storage storage : model.getStorages()) {
                storageEntities.add(storageMapper.map(storage));
            }
            entity.setStorages(storageEntities);
        }

        // SKILL
        if (!excludedDependencies.contains(Skill.class)) {
            final SkillMapper skillMapper = MapperManager.getDatabaseMapper(SkillMapper.class);
            final Set<SkillEntity> skillEntities = new FastSet<>();
            for (final Skill skill : model.getSkillList().getSkills()) {
                final SkillEntity skillEntity = skillMapper.map(skill);
                skillEntity.setPlayer(entity);
                skillEntities.add(skillEntity);
            }
            entity.setSkills(skillEntities);
        }

        // GATHER
        if (!excludedDependencies.contains(Gather.class)) {
            final GatherMapper gatherMapper = MapperManager.getDatabaseMapper(GatherMapper.class);
            final Set<GatherEntity> gatherEntities = new FastSet<>();
            for (final Gather gather : model.getGatherStats().getGathers()) {
                final GatherEntity gatherEntity = gatherMapper.map(gather);
                gatherEntity.setPlayer(entity);
                gatherEntities.add(gatherEntity);
            }

            entity.setGathers(gatherEntities);
        }

        // ACHIEVEMENTS
        if (!excludedDependencies.contains(Achievement.class)) {
            final AchievementMapper achievementMapper = MapperManager.getDatabaseMapper(AchievementMapper.class);
            final Set<AchievementEntity> achievementEntities = new FastSet<>();
            for (final Achievement achievement : model.getAchievements()) {
                final AchievementEntity achievementEntity = achievementMapper.map(achievement);
                achievementEntity.setPlayer(entity);
                achievementEntities.add(achievementEntity);
            }
            entity.setAchievements(achievementEntities);
        }
    }

    // ENTITY -> MODEL
    @Override
    protected Player createNewEmptyModel() {
        return new Player();
    }

    @Override
    public void map(final PlayerEntity entity, final Player model) {
        // DIRECT
        model.setName(entity.getName());
        model.setGender(entity.getGender());
        model.setRace(entity.getRace());
        model.setPlayerClass(entity.getPlayerClass());
        model.setLevel(entity.getLevel());
        model.setExperience(entity.getExperience());
        model.setCurrentRestedExperience(entity.getCurrentRestedExperience());
        model.setMaxRestedExperience(entity.getMaxRestedExperience());
        model.setCreationTime(entity.getCreationTime());
        model.setDeletionTime(entity.getDeletionTime());
        model.setLastOnlineTime(entity.getLastOnlineTime());
        model.setOnline(entity.isOnline());
        model.setDescription(entity.getDescription());
        model.setUserSettings(entity.getUserSettings());
        model.setTitle(entity.getTitle());
        model.setPlayerMode(entity.getPlayerMode());
        model.setCurrentZoneData(entity.getCurrentZoneData());

        // CREATURE STATS
        final CurrentStats currentStats = new CurrentStats();
        currentStats.setHp(entity.getHp());
        currentStats.setMp(entity.getMp());
        currentStats.setStamina(entity.getStamina());
        model.setCurrentStats(currentStats);

        // WORLD POSITION
        final WorldPosition worldPosition = new WorldPosition();
        worldPosition.setMapId(entity.getMapId());
        worldPosition.setX(entity.getX());
        worldPosition.setY(entity.getY());
        worldPosition.setZ(entity.getZ());
        worldPosition.setHeading(entity.getHeading());
        model.setWorldPosition(worldPosition);

        // PLAYER APPEARANCE
        final PlayerAppearance playerAppearance = new PlayerAppearance();
        playerAppearance.setData(entity.getData());
        playerAppearance.setDetails1(entity.getDetails1());
        playerAppearance.setDetails2(entity.getDetails2());
        model.setPlayerAppearance(playerAppearance);
    }

    @Override
    protected void finalizeDependencies(final PlayerEntity entity, final Player model, final List<Class<? extends AbstractDatabaseEntity>> excludedDependencies) {
        final List<Class<? extends AbstractDatabaseEntity>> dependenciesToExclude = CollectionUtils.createListFrom(PlayerEntity.class);

        // ACCOUNT
        if (!excludedDependencies.contains(AccountEntity.class)) {
            final AccountMapper accountMapper = MapperManager.getDatabaseMapper(AccountMapper.class);
            model.setAccount(accountMapper.map(entity.getAccount(), dependenciesToExclude));
        }

        // CRAFT
        if (!excludedDependencies.contains(CraftEntity.class)) {
            final CraftMapper craftMapper = MapperManager.getDatabaseMapper(CraftMapper.class);

            final Set<Craft> crafts = new FastSet<>();
            for (final CraftEntity craftEntity : entity.getCrafts()) {
                final Craft craft = craftMapper.map(craftEntity);
                crafts.add(craft);
            }
            model.setCraftStats(new CraftStats(crafts));
        }

        // STORAGE
        if (!excludedDependencies.contains(StorageEntity.class)) {
            final StorageMapper storageMapper = MapperManager.getDatabaseMapper(StorageMapper.class);
            final Set<Storage> storages = new FastSet<>();
            for (final StorageEntity storageEntity : entity.getStorages()) {
                storages.add(storageMapper.map(storageEntity));
            }
            model.setStorages(storages);
        }

        // SKILL
        if (!excludedDependencies.contains(SkillEntity.class)) {
            final SkillMapper skillMapper = MapperManager.getDatabaseMapper(SkillMapper.class);
            final Set<Skill> skills = new FastSet<>();
            for (final SkillEntity skillEntity : entity.getSkills()) {
                skills.add(skillMapper.map(skillEntity));
            }
            model.setSkillList(new SkillList(skills));
        }

        // GATHER
        if (!excludedDependencies.contains(GatherEntity.class)) {
            final GatherMapper gatherMapper = MapperManager.getDatabaseMapper(GatherMapper.class);
            final Set<Gather> gathers = new FastSet<>();
            for (final GatherEntity gatherEntity : entity.getGathers()) {
                gathers.add(gatherMapper.map(gatherEntity));
            }
            model.setGatherStats(new GatherStats(gathers));
        }

        // ACHIEVEMENTS
        if (!excludedDependencies.contains(AchievementEntity.class)) {
            final AchievementMapper achievementMapper = MapperManager.getDatabaseMapper(AchievementMapper.class);
            final List<Achievement> achievements = new FastList<>();
            for (final AchievementEntity achievementEntity : entity.getAchievements()) {
                achievements.add(achievementMapper.map(achievementEntity));
            }
            model.setAchievements(achievements);
        }

        // VISITED ZONES
        final ZoneMapper zoneMapper = MapperManager.getDatabaseMapper(ZoneMapper.class);
        final Set<Zone> zones = new FastSet<>();
        for (final ZoneEntity zoneEntity : entity.getVisitedZones()) {
            zones.add(zoneMapper.map(zoneEntity));
        }
        model.setVisitedZones(zones);

        // QUEST
        final QuestMapper questMapper = MapperManager.getDatabaseMapper(QuestMapper.class);
        final QuestList questList = new QuestList();
        // TODO
        model.setQuestList(questList);

        // RELATIONS
        // TODO
    }

    @Override
    public void merge(final PlayerEntity currentEntity, final PlayerEntity existingEntity) {
        BeanUtils.fill(PlayerEntity.class, currentEntity, existingEntity);
    }
}
