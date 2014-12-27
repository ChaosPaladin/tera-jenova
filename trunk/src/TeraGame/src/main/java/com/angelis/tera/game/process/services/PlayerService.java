package com.angelis.tera.game.process.services;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javolution.util.FastList;
import javolution.util.FastMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.angelis.tera.common.process.services.AbstractService;
import com.angelis.tera.game.config.GlobalConfig;
import com.angelis.tera.game.config.PlayerConfig;
import com.angelis.tera.game.domain.entity.xml.players.PlayerExperienceEntity;
import com.angelis.tera.game.domain.entity.xml.players.PlayerExperienceEntityHolder;
import com.angelis.tera.game.presentation.network.ChatMessages;
import com.angelis.tera.game.presentation.network.SystemMessages;
import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.enums.CheckCharacterNameResponse;
import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;
import com.angelis.tera.game.presentation.network.packet.server.SM_FESTIVAL_LIST;
import com.angelis.tera.game.presentation.network.packet.server.SM_LOAD_TOPO;
import com.angelis.tera.game.presentation.network.packet.server.SM_QUIT_GAME_WINDOW;
import com.angelis.tera.game.presentation.network.packet.server.SM_QUIT_TO_CHARACTER_LIST_WINDOW;
import com.angelis.tera.game.presentation.network.packet.server.SM_SOCIAL;
import com.angelis.tera.game.presentation.network.packet.server.character.SM_CHARACTER_CREATE;
import com.angelis.tera.game.presentation.network.packet.server.character.SM_CHARACTER_CREATE_NAME_USED_CHECK;
import com.angelis.tera.game.presentation.network.packet.server.character.SM_CHARACTER_DELETE;
import com.angelis.tera.game.presentation.network.packet.server.character.SM_CHARACTER_RESTORE;
import com.angelis.tera.game.presentation.network.packet.server.player.SM_PLAYER_BLOCK_ADD_SUCCESS;
import com.angelis.tera.game.presentation.network.packet.server.player.SM_PLAYER_DEATH;
import com.angelis.tera.game.presentation.network.packet.server.player.SM_PLAYER_DEATH_WINDOW;
import com.angelis.tera.game.presentation.network.packet.server.player.SM_PLAYER_EXPERIENCE_UPDATE;
import com.angelis.tera.game.presentation.network.packet.server.player.SM_PLAYER_FRIEND_ADD_SUCCESS;
import com.angelis.tera.game.presentation.network.packet.server.player.SM_PLAYER_FRIEND_LIST;
import com.angelis.tera.game.presentation.network.packet.server.player.SM_PLAYER_FRIEND_REMOVE_SUCCESS;
import com.angelis.tera.game.presentation.network.packet.server.player.SM_PLAYER_REVIVE;
import com.angelis.tera.game.presentation.network.packet.server.player.SM_PLAYER_SELECT_CREATURE;
import com.angelis.tera.game.presentation.network.packet.server.player.SM_PLAYER_SET_TITLE;
import com.angelis.tera.game.presentation.network.packet.server.player.SM_PLAYER_STATS_UPDATE;
import com.angelis.tera.game.process.delegate.database.PlayerDelegate;
import com.angelis.tera.game.process.model.account.Account;
import com.angelis.tera.game.process.model.chainedaction.AbstractChainedAction;
import com.angelis.tera.game.process.model.chainedaction.GatherChainedAction;
import com.angelis.tera.game.process.model.creature.AbstractCreature;
import com.angelis.tera.game.process.model.creature.Creature;
import com.angelis.tera.game.process.model.creature.CurrentStats;
import com.angelis.tera.game.process.model.creature.VisibleObjectEventTypeEnum;
import com.angelis.tera.game.process.model.enums.CheckNameTypeEnum;
import com.angelis.tera.game.process.model.enums.ObjectFamilyEnum;
import com.angelis.tera.game.process.model.enums.StorageTypeEnum;
import com.angelis.tera.game.process.model.gather.Gather;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.model.player.PlayerRelation;
import com.angelis.tera.game.process.model.player.enums.EmoteEnum;
import com.angelis.tera.game.process.model.player.enums.GenderEnum;
import com.angelis.tera.game.process.model.player.enums.PlayerClassEnum;
import com.angelis.tera.game.process.model.player.enums.PlayerModeEnum;
import com.angelis.tera.game.process.model.player.enums.PlayerMoveTypeEnum;
import com.angelis.tera.game.process.model.player.enums.RaceEnum;
import com.angelis.tera.game.process.model.skill.SkillArgs;
import com.angelis.tera.game.process.model.visible.WorldPosition;
import com.angelis.tera.game.process.model.visible.enums.VisibleTypeEnum;
import com.angelis.tera.game.process.tasks.AbstractTask;
import com.angelis.tera.game.process.tasks.TaskTypeEnum;
import com.angelis.tera.game.process.tasks.creature.CreatureStatsModifierTask;
import com.angelis.tera.game.process.tasks.player.PlayerAutoSaveTask;
import com.angelis.tera.game.process.tasks.player.PlayerDeleteTask;
import com.angelis.tera.game.process.tasks.player.PlayerQuitTask;
import com.angelis.tera.game.process.tasks.player.PlayerQuitToPlayerListTask;
import com.angelis.tera.game.utils.DateUtils;
import com.angelis.tera.game.utils.GroupUtils;

public class PlayerService extends AbstractService {

    /** LOGGER */
    private static Logger log = Logger.getLogger(PlayerService.class.getName());
    
    /** DELEGATES */
    private final PlayerDelegate playerDelegate = new PlayerDelegate();
    
    private final Map<Integer, Long> experiences = new FastMap<>();

    private PlayerService() {
    }

    @Override
    public void onInit() {
        for (final PlayerExperienceEntity experienceEntity : XMLService.getInstance().getEntity(PlayerExperienceEntityHolder.class).getExperiences()) {
            experiences.put(experienceEntity.getLevel(), experienceEntity.getExperience());
        }
        XMLService.getInstance().clearEntity(PlayerExperienceEntityHolder.class);
        
        log.info("PlayerService started");
    }

    @Override
    public void onDestroy() {
        log.info("PlayerService stopped");
    }

    // ---- EVENT ---- //
    public final void onPlayerCreate(final Player player, final boolean creationSuccess) {
        player.getConnection().sendPacket(new SM_CHARACTER_CREATE(creationSuccess));
    }

    public final void onPlayerDelete(final Player player) {
        player.getConnection().sendPacket(new SM_CHARACTER_DELETE(true));
    }
    
    public final void onPlayerRestore(final Player player) {
        player.getConnection().sendPacket(new SM_CHARACTER_RESTORE(true));
    }

    public final void onPlayerConnect(final Player player) {
        player.setOnline(true);
        player.setPlayerMode(PlayerModeEnum.NORMAL);
        if (player.getCurrentRestedExperience() == 0 && player.getLastOnlineTime() != null) { // TODO
            final int restedExperience = (int) ((System.currentTimeMillis()-player.getLastOnlineTime().getTime())/60*PlayerConfig.PLAYER_REST_RATE);
            player.setCurrentRestedExperience(restedExperience);
            player.setMaxRestedExperience(restedExperience);
        }
        
        BaseStatService.getInstance().onPlayerConnect(player);
        WorldService.getInstance().onPlayerConnect(player);

        ThreadPoolService.getInstance().scheduleTask(new PlayerAutoSaveTask(player), PlayerConfig.PLAYER_AUTO_SAVE_DELAY, TimeUnit.SECONDS);
        ThreadPoolService.getInstance().scheduleRepeatableTask(new CreatureStatsModifierTask(player), 1, 1, TimeUnit.SECONDS);
    }

    public void onPlayerDisconnect(final Player player) {
        player.setOnline(false);
        player.setLastOnlineTime(new Date());
        player.setGroup(null);
        
        ThreadPoolService.getInstance().cancelAllTasksByLinkedObject(player);
        ObjectIDService.getInstance().releaseId(ObjectFamilyEnum.fromClass(player.getClass()), player.getUid());

        WorldService.getInstance().onPlayerDisconnect(player);
        VisibleService.getInstance().onPlayerDisconnect(player);
        MountService.getInstance().onPlayerDisconnect(player);
        RequestService.getInstance().onPayerDisconnect(player);
    }

    public void onPlayerCheckNameUsed(final TeraGameConnection connection, final short type, final String name) {
        connection.sendPacket(new SM_CHARACTER_CREATE_NAME_USED_CHECK((playerDelegate.findByName(name) == null)));
    }

    public void onPlayerMove(final Player player, final float x1, final float y1, final float z1, final short heading, final float x2, final float y2, final float z2, final PlayerMoveTypeEnum moveType, final byte[] unk2, final int unk3) {
        final WorldPosition worldPosition = player.getWorldPosition();
        worldPosition.setXYZ(x1, y1, z1);
        worldPosition.setHeading(heading);

        VisibleService.getInstance().onPlayerMove(player);
        DialogService.getInstance().onPlayerMove(player);
        BattleService.getInstance().onPlayerMove(player);
        GroupService.getInstance().onPlayerMove(player);
        
        final AbstractChainedAction chainedAction = player.getController().getChainedAction();
        if (chainedAction != null) {
            chainedAction.cancel();
        }
        
        player.getKnownList().notify(VisibleTypeEnum.PLAYER, VisibleObjectEventTypeEnum.CREATURE_MOVE, x1, y1, z1, heading, x2, y2, z2, moveType, unk2, unk3);
        player.getKnownList().notify(VisibleTypeEnum.CAMPFIRE, VisibleObjectEventTypeEnum.CREATURE_MOVE, x1, y1, z1, heading, x2, y2, z2, moveType, unk2, unk3);
    }

    public void onPlayerEmote(final Player player, final EmoteEnum emote, final int duration) {
        final List<TeraServerPacket> packets = new FastList<>();
        packets.add(new SM_SOCIAL(player, emote, duration));
        packets.add(ChatMessages.emote(player, emote));
        VisibleService.getInstance().sendPacketsForVisible(player, packets);
    }

    public void onPlayerSetTitle(final Player player, final int title) {
        player.setTitle(title);
        VisibleService.getInstance().sendPacketForVisible(player, new SM_PLAYER_SET_TITLE(player, title));
    }

    public void onPlayerLevelUp(final Player player) {
        BaseStatService.getInstance().onPlayerLevelUp(player);
        QuestService.getInstance().onPlayerLevelUp(player);
        
        final TeraGameConnection connection = player.getConnection();
        connection.sendPacket(SystemMessages.CONGRATULATION_YOU_ARE_LEVEL(String.valueOf(player.getLevel())));
        connection.sendPacket(new SM_PLAYER_STATS_UPDATE(player));
    }

    public void onPlayerBlock(final Player player, final String playerName) {
        final Player targetPlayer = playerDelegate.findByName(playerName);
        final TeraGameConnection connection = player.getConnection();

        if (targetPlayer == null) {
            connection.sendPacket(SystemMessages.FRIEND_ADD_UNKNOWN_TARGET());
            return;
        }

        if (player.equals(targetPlayer)) {
            connection.sendPacket(SystemMessages.FRIEND_ADD_INVALID_TARGET());
            return;
        }

        player.getRelations().add(new PlayerRelation(targetPlayer, false));
        connection.sendPacket(new SM_PLAYER_BLOCK_ADD_SUCCESS(targetPlayer));
    }

    public void onPlayerBlockNote(final Player player, final int playerId, final String note) {
        player.getRelationById(playerId).setNote(note);
    }

    public void onPlayerUnblock(final Player activePlayer, final int playerId) {

    }

    public void onPlayerFriendAdd(final Player player, final String playerName) {
        final Player targetPlayer = playerDelegate.findByName(playerName);
        final TeraGameConnection connection = player.getConnection();

        if (targetPlayer == null) {
            connection.sendPacket(SystemMessages.FRIEND_ADD_UNKNOWN_TARGET());
            return;
        }

        if (player.equals(targetPlayer)) {
            connection.sendPacket(SystemMessages.FRIEND_ADD_INVALID_TARGET());
            return;
        }

        player.getRelations().add(new PlayerRelation(targetPlayer, true));
        connection.sendPacket(new SM_PLAYER_FRIEND_ADD_SUCCESS(targetPlayer));
        connection.sendPacket(SystemMessages.FRIEND_ADD_SUCCESS(playerName));
        connection.sendPacket(new SM_PLAYER_FRIEND_LIST(player));

        targetPlayer.getRelations().add(new PlayerRelation(player, true));
        if (targetPlayer.getConnection() != null) {
            targetPlayer.getConnection().sendPacket(SystemMessages.FRIEND_ADDED_YOU(player.getName()));
            targetPlayer.getConnection().sendPacket(new SM_PLAYER_FRIEND_LIST(targetPlayer));
        }
    }

    public void onPlayerFriendRemove(final Player player, final int playerId) {
        final Player targetPlayer = playerDelegate.findById(playerId);
        final TeraGameConnection connection = player.getConnection();

        if (targetPlayer == null) {
            connection.sendPacket(SystemMessages.FRIEND_ADD_UNKNOWN_TARGET());
            return;
        }

        player.getRelations().remove(targetPlayer);
        connection.sendPacket(new SM_PLAYER_FRIEND_REMOVE_SUCCESS(targetPlayer));
        connection.sendPacket(SystemMessages.FRIEND_REMOVE_SUCCESS(targetPlayer.getName()));
        connection.sendPacket(new SM_PLAYER_FRIEND_LIST(player));

        targetPlayer.getRelations().remove(player);
        if (targetPlayer.getConnection() != null) {
            targetPlayer.getConnection().sendPacket(SystemMessages.FRIEND_REMOVED_YOU(player.getName()));
            targetPlayer.getConnection().sendPacket(new SM_PLAYER_FRIEND_LIST(player));
        }
    }

    public void onPlayerFriendNote(final Player player, final int playerId, final String note) {
        player.getRelationById(playerId).setNote(note);
    }

    public void onPlayerSelectCreature(final Player player, final int uid, final ObjectFamilyEnum objectFamily) {
        final AbstractCreature creature = (AbstractCreature) ObjectIDService.getInstance().getObjectByUId(objectFamily, uid);
        if (creature == null) {
            return;
        }
        
        player.getConnection().sendPacket(new SM_PLAYER_SELECT_CREATURE(creature));
    }
    
    public void onPlayerAttack(final Player player, final SkillArgs attackArgs) {
        
    }
    
    public void onPlayerDie(final Player player) {
        final CurrentStats currentStats = player.getCurrentStats();
        if (currentStats.isDead()) {
            return;
        }

        currentStats.setHp(0);
        currentStats.setMp(0);
        currentStats.setStamina(0);
        
        final TeraGameConnection connection = player.getConnection();
        
        VisibleService.getInstance().sendPacketForVisible(player, new SM_PLAYER_DEATH(player));
        connection.sendPacket(new SM_PLAYER_STATS_UPDATE(player));
        connection.sendPacket(SystemMessages.YOU_ARE_DEAD(player.getName()));
        connection.sendPacket(new SM_PLAYER_DEATH_WINDOW());

        if (player.getGroup() != null) {
            GroupService.getInstance().onPlayerDie(player);
        }
    }
    
    public void onPlayerRevive(final Player player, final int choice) {
        final CurrentStats currentStats = player.getCurrentStats();
        currentStats.setHp(10);
        
        player.getConnection().sendPacket(new SM_PLAYER_REVIVE());
        player.getConnection().sendPacket(new SM_FESTIVAL_LIST());
        player.getConnection().sendPacket(new SM_LOAD_TOPO(player.getWorldPosition()));
        
        if (player.getGroup() != null) {
            GroupService.getInstance().onPlayerRevive(player);
        }
    }
    
    public void onPlayerGatherStart(final Player player, final ObjectFamilyEnum objectFamily, final Integer uid) {
        if (player.getController().getChainedAction() != null) {
            // player is already processing another chained action
            return;
        }
        
        final Gather gather = (Gather) ObjectIDService.getInstance().getObjectByUId(objectFamily, uid);
        AbstractChainedAction chainedAction = null;
        if (gather.getGatherer() != null) {
            if (!GroupUtils.isInSameGroup(player, gather.getGatherer())) {
                return;
            }

            chainedAction = gather.getGatherer().getController().getChainedAction();
        } else {
            chainedAction = new GatherChainedAction(player, gather);
        }
        
        player.getController().setChainedAction(chainedAction);
        chainedAction.start();
    }
    
    public void onPlayerUpdateExperience(final Player player, final Creature creature, final int experience) {
        
        final long requiredExperience = this.experiences.get(player.getLevel());
        int experienceDelta = experience;
        if ((player.getExperience()+experience) > requiredExperience) { // TODO
            experienceDelta = (int) ((player.getExperience()+experience) - requiredExperience);
            this.levelUpPlayer(player, player.getLevel()+1);
        }
        
        player.addExperience(experienceDelta);
        player.getConnection().sendPacket(new SM_PLAYER_EXPERIENCE_UPDATE(player, creature, experience));
    }

    // ---- EVENT ---- //

    public CheckCharacterNameResponse checkNamePattern(final CheckNameTypeEnum type, final String name) {
        if (name.length() < type.minLength) {
            return CheckCharacterNameResponse.NOT_LONG_ENOUGH;
        }

        if (name.length() > type.maxLength) {
            return CheckCharacterNameResponse.TOO_LONG;
        }

        if (!type.spaceAllowed && StringUtils.containsWhitespace(name)) {
            return CheckCharacterNameResponse.MUST_NOT_CONTAINS_SPACE;
        }

        if (!name.matches(PlayerConfig.PLAYER_NAME_PATTERN)) {
            return CheckCharacterNameResponse.MUST_BE_ALPHABETIC;
        }

        for (final String bannedWordName : GlobalConfig.GLOBAL_NAME_BANNED_WORDS) {
            if (StringUtils.contains(name.toLowerCase(), bannedWordName)) {
                return CheckCharacterNameResponse.MUST_NOT_CONTAINS_BANNED_WORD;
            }
        }

        return CheckCharacterNameResponse.OK;
    }

    public void registerPlayer(final TeraGameConnection connection, final Player player) {
        connection.setActivePlayer(player);
        player.setAccount(connection.getAccount());
        player.setLastOnlineTime(new Date());
        this.onPlayerConnect(player);
    }

    /** CRUD OPERATIONS */
    public void createPlayer(final TeraGameConnection connection, final Player player) {
        final Account account = connection.getAccount();
        player.setAccount(account);
        account.addPlayer(player);
        
        // Fix for elin & popori
        if (player.getRace().value == 4) {
            if (player.getGender() == GenderEnum.FEMALE) {
                player.setRace(RaceEnum.ELIN);
            } else {
                player.setRace(RaceEnum.POPORI);
            }
        }
        player.setGatherCraftPoints(4000);
        player.setStockExchangeItems(new FastList<>());

        final boolean creationSuccess = checkPlayerRequirements(account, player);
        if (creationSuccess) {
            // TODO
            player.setUserSettings("0800B0020897561204322343505250089756522E5300310053006B0069006C006C0048006F0074004B006500790043006F006E00740072006F006C006C0065007200A201140897564A0433003200520908E907100018012028F00100C002145271089756522853003100530068006F007200740043007500740043006F006E00740072006F006C006C0065007200A2013B78DAE3981E16C0B08091F11013070783009B448A42ED21264E20931DC83C6E07614B483028DCF03FC4C405644B02D92D7319273033000069400BC0F00101C0023B5269089756523653003100450071007500690070006D0065006E00740050007200650073006500740043006F006E00740072006F006C006C0065007200A2012478DAE3D81F26C421C020C1A0C0A0C130CA1A658DB24659C38D250187409E2400DBEB4559F00101C002F309524408975652265300310043007500730074006F006D0069007A0065004B0065007900470072006F0075007000A20110089756120B10E0011800200028003000F00100C00210525B08975652325300310043007500730074006F006D0069007A00650050006100640042007500740074006F006E00470072006F0075007000A2011B0897561A16580062006F007800540079007000650053005F005800F00100C0021B523B0897565228530031004E00500043004700750069006C00640043006F006E00740072006F006C006C0065007200A201050897565000F00100C00205525908975652385300310053006B0069006C006C004300720065007300740050007200650073006500740043006F006E00740072006F006C006C0065007200A2011378DAE3D81827C100830AFF6180110057F60AA8F00101C00218523A089756522A5300310049006E00760065006E0074006F007200790043006F006E00740072006F006C006C0065007200A201020800F00100C00202".getBytes());
            
            QuestService.getInstance().onPlayerCreate(player);
            StorageService.getInstance().onPlayerCreate(player);
            WorldService.getInstance().onPlayerCreate(player);
            BaseStatService.getInstance().onPlayerCreate(player);
            SkillService.getInstance().onPlayerCreate(player);
            CraftService.getInstance().onPlayerCreate(player);
            GatherService.getInstance().onPlayerCreate(player);
            AchievementService.getInstance().onPlayerCreate(player);
    
            playerDelegate.create(player);
        }

        this.onPlayerCreate(player, creationSuccess);
    }

    public void deletePlayer(final Player player) {
        if (player == null) {
            return;
        }

        final Date deletionTime = DateUtils.getNow();
        DateUtils.addSeconds(deletionTime, 52); // TODO time is level based
        player.setDeletionTime(deletionTime); 
        ThreadPoolService.getInstance().scheduleTask(new PlayerDeleteTask(player), PlayerConfig.PLAYER_DELETE_TIMEOUT, TimeUnit.SECONDS);
        this.onPlayerDelete(player);
    }

    public void restorePlayer(final Player player) {
        if (player == null) {
            return;
        }

        player.setDeletionTime(null);
        ThreadPoolService.getInstance().cancelTask(player, TaskTypeEnum.PLAYER_DELETE);
        this.onPlayerRestore(player);
    }

    public void levelUpPlayer(final Player player, final int level) {
        if (level > PlayerConfig.PLAYER_MAX_LEVEL) {
            return;
        }

        Long experienceNeeded = this.experiences.get(level-1);
        if (experienceNeeded == null) {
            experienceNeeded = 0L;
        }
        player.setExperience(experienceNeeded);
        
        player.setLevel(level);
        this.onPlayerLevelUp(player);
    }
    
    public void teleportPlayer(final Player player, final int mapId, final float x, final float y, final float z) {
        final WorldPosition worldPosition = player.getWorldPosition();
        worldPosition.setMapId(mapId);
        worldPosition.setXYZ(x, y, z);
        
        player.getKnownList().clear();
        player.getConnection().sendPacket(new SM_LOAD_TOPO(player.getWorldPosition()));
        try {
            Thread.sleep(2000);
        }
        catch (final InterruptedException e) {}
        player.getKnownList().update();
    }
    
    public void teleportPlayer(final Player player, final WorldPosition worldPosition) {
        this.teleportPlayer(player, worldPosition.getMapId(), worldPosition.getX(), worldPosition.getY(), worldPosition.getZ());
    }
    
    public long getExpShown(final Player player) {
        final int level = player.getLevel();
        if (level == (this.experiences.size()-1)) {
            return this.experiences.get(level);
        }
        
        Long experienceGained = this.experiences.get(level-1);
        if (experienceGained == null) {
            experienceGained = 0L;
        }
        return player.getExperience() - experienceGained;
    }
    
    public long getExpNeeded(final Player player) {
        return this.experiences.get(player.getLevel());
    }
    
    public void addMoney(final Player player, final Integer quantity) {
        player.getStorage(StorageTypeEnum.INVENTORY).addMoney(quantity);
    }
    
    public void removeMoney(final Player player, final Integer quantity) {
        player.getStorage(StorageTypeEnum.INVENTORY).removeMoney(quantity);
    }
    
    private boolean checkPlayerRequirements(final Account account, final Player player) {
        boolean creationSuccess = true;
        
        // Admin accounts doesn't have any restrictions
        if (account.getAccess() == 0) {
            if (player.getPlayerClass() == PlayerClassEnum.REAPER) {
                if (!account.haveCharacterWithLevel(PlayerConfig.PLAYER_REAPER_REQUIRE_PLAYER_MIN_LEVEL)) {
                    creationSuccess = false;
                }
            }
        }
        return creationSuccess;
    }

    /** SINGLETON */
    public static PlayerService getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        protected static final PlayerService instance = new PlayerService();
    }

    public void onPlayerQuit(final Player player, final boolean quitGame) {
        if (player.getActivePegasus() != null) {
            // TODO
            return;
        }
        
        int delay = 0;
        AbstractTask<Player> task = null;
        if (quitGame) {
            delay = PlayerConfig.PLAYER_QUIT_TIMEOUT;
            if (player.getConnection().getAccount().getAccess() > 0) {
                delay = 0;
            }
            
            if (delay != 0) {
                player.getConnection().sendPacket(new SM_QUIT_GAME_WINDOW(delay));
            }
            
            task = new PlayerQuitTask(player);
        }
        else {
            delay = PlayerConfig.PLAYER_QUIT_TIMEOUT;
            if (player.getConnection().getAccount().getAccess() > 0) {
                delay = 0;
            }
            
            if (delay != 0) {
                player.getConnection().sendPacket(new SM_QUIT_TO_CHARACTER_LIST_WINDOW(delay));
            }
            
            task = new PlayerQuitToPlayerListTask(player);
        }
        
        ThreadPoolService.getInstance().scheduleTask(task, delay == 0 ? 0 : delay+1, TimeUnit.SECONDS);
    }
}
