package com.angelis.tera.game.presentation.network.packet;

import java.util.Map;
import java.util.Map.Entry;

import javolution.util.FastMap;

import org.apache.log4j.Logger;

import com.angelis.tera.common.network.packet.AbstractServerPacket;
import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.server.*;
import com.angelis.tera.game.presentation.network.packet.server.key.KeyServerPacket;

public class ServerPacketHandler {

    /** LOGGER */
    private static Logger log = Logger.getLogger(ServerPacketHandler.class.getName());

    private static Map<Short, Class<? extends AbstractServerPacket<TeraGameConnection>>> serverPackets = new FastMap<Short, Class<? extends AbstractServerPacket<TeraGameConnection>>>();

    public static final void init() {
        serverPackets.clear();

        // AUTH
        addPacket((short) 0x4DBD, SM_CHECK_VERSION.class); // OK
        addPacket((short) 0xDC28, SM_LOADING_SCREEN_CONTROL_INFO.class); // OK
        addPacket((short) 0x521E, SM_REMAIN_PLAY_TIME.class); // OK
        addPacket((short) 0xE9DE, SM_LOGIN_ARBITER.class); // OK
        addPacket((short) 0xACC6, SM_LOGIN_ACCOUNT_INFO.class); // OK
        addPacket((short) 0x8093, SM_ACCOUNT_PACKAGE_LIST.class); // OK
        addPacket((short) 0xC8A8, SM_SYSTEM_INFO.class); // OK

        // CHARACTER
        addPacket((short) 0x65C6, SM_CHARACTER_LIST.class); // OK
        addPacket((short) 0x6779, SM_CHARACTER_CREATE_ALLOWED.class); // OK
        addPacket((short) 0xB743, SM_CHARACTER_CREATE_NAME_PATTERN_CHECK.class); // OK
        addPacket((short) 0xB5C4, SM_CHARACTER_CREATE_NAME_USED_CHECK.class); // OK
        addPacket((short) 0x89C6, SM_CHARACTER_CREATE.class); // OK
        addPacket((short) 0xF9E8, SM_CHARACTER_DELETE.class); // OK
        addPacket((short) 0xCCE0, SM_CHARACTER_RESTORE.class); // OK

        // ENTER WORLD
        addPacket((short) 0x5CCF, SM_ENTER_WORLD.class); // OK
        addPacket((short) 0xD61A, SM_LOGIN.class); // OK
        addPacket((short) 0xF5AF, SM_CURRENT_ELECTION_STATE.class);
        addPacket((short) 0x9274, SM_AVAILABLE_SOCIAL_LIST.class); // OK
        addPacket((short) 0x969C, SM_NPC_GUILD_LIST.class); // OK
        addPacket((short) 0xA33A, SM_VIRTUAL_LATENCY.class); // OK
        addPacket((short) 0x539D, SM_MOVE_DISTANCE_DELTA.class); // OK
        addPacket((short) 0xAC2B, SM_F2P_PREMIUM_USER_PERMISSION.class); // OK
        addPacket((short) 0x86B7, SM_PLAYER_EQUIP_ITEM_CHANGER.class); // Not sure
        addPacket((short) 0xD85D, SM_FESTIVAL_LIST.class); // OK
        addPacket((short) 0xF1AD, SM_MASSTIGE_STATUS.class); // OK
        addPacket((short) 0x792B, SM_LOAD_TOPO.class); // OK
        addPacket((short) 0xA953, SM_LOAD_HINT.class); // OK
        addPacket((short) 0xA2D8, SM_SIMPLE_TIP_REPEATED_CHECK.class); // OK
        addPacket((short) 0x8F16, SM_USER_SETTINGS_LOAD.class); // OK
        addPacket((short) 0xA7DE, SM_MOVIE_PLAY.class);
        addPacket((short) 0x96BD, SM_VISITED_SECTION_LIST.class);
        addPacket((short) 0x5EB0, SM_TRADEBROKER_HIGHEST_ITEM_LEVEL.class); // OK
        addPacket((short) 0x8A2B, SM_ACHIEVEMENT_PROGRESS_UPDATE.class); // OK
        
        // PEGASUS
        addPacket((short) 0xBE73, SM_PEGASUS_START.class);
        addPacket((short) 0xF49C, SM_PEGASUS_UPDATE.class);
        addPacket((short) 0xE502, SM_PEGASUS_END.class);
        addPacket((short) 0xD4E5, SM_PEGASUS_MAP_SHOW.class);

        // MOUNT
        addPacket((short) 0xFB59, SM_PLAYER_MOUNT.class);
        addPacket((short) 0xCF34, SM_PLAYER_UNMOUNT.class);

        // GUILD
        addPacket((short) 0xB728, SM_SERVERGUILD_WINDOW_OPEN.class);
        
        // PET
        addPacket((short) 0xDD08, SM_PET_INCUBATOR_INFO_CHANGE.class); // OK
        addPacket((short) 0x8908, SM_PET_INFO_CLEAR.class); // OK

        // ALLIANCE
        addPacket((short) 0xEB1B, SM_ALLIANCE_INFO.class); // OK

        // ATTACK
        addPacket((short) 0xAE89, SM_ACTION_STAGE.class);
        addPacket((short) 0xD947, SM_ACTION_END.class);
        addPacket((short) 0xD3A1, SM_CREATURE_INSTANCE_ARROW.class);
        addPacket((short) 0x64DA, SM_PLAYER_EXPERIENCE_UPDATE.class);

        // OPTIONS
        addPacket((short) 0xB4C9, SM_OPTION_SHOW_MASK.class);

        // SKILL
        addPacket((short) 0xA2F7, SM_SKILL_START_COOLTIME.class);
        addPacket((short) 0x0001, SM_SKILL_PERIOD.class);
        addPacket((short) 0xD19A, SM_SKILL_RESULTS.class);
        addPacket((short) 0xDEC3, SM_PLAYER_SKILL_LIST.class); // OK

        // CHAT
        addPacket((short) 0xC4DE, SM_CHAT.class); // OK
        addPacket((short) 0xB858, SM_CHAT_INFO.class);
        addPacket((short) 0xBB4D, SM_WHISP.class);
        
        // PLAYER
        addPacket((short) 0x7064, SM_PLAYER_FRIEND_LIST.class); // OK
        addPacket((short) 0x840C, SM_OWN_PLAYER_SPAWN.class); // OK
        addPacket((short) 0xCD87, SM_PLAYER_STATS_UPDATE.class); // OK
        addPacket((short) 0xAD17, SM_PLAYER_MOVE.class);
        addPacket((short) 0x5133, SM_PLAYER_ZONE_CHANGE.class);
        addPacket((short) 0xC258, SM_PLAYER_SELECT_CREATURE.class);
        addPacket((short) 0xB195, SM_PLAYER_STATE.class);
        addPacket((short) 0xA3C0, SM_RESPONSE_GAMESTAT_PONG.class);
        addPacket((short) 0x6022, SM_PLAYER_DONJON_CLEAR_COUNT_LIST.class);
        addPacket((short) 0xCF79, SM_PLAYER_SPAWN.class);
        addPacket((short) 0xEF19, SM_PLAYER_DESPAWN.class);
        addPacket((short) 0xC7A3, SM_PLAYER_CLIMB_START.class);
        addPacket((short) 0xDFDC, SM_PLAYER_DESCRIPTION.class); // OK
        addPacket((short) 0xA017, SM_PLAYER_UNK1.class);
        addPacket((short) 0x573F, SM_PLAYER_UNK2.class);
        addPacket((short) 0xA3C6, SM_PLAYER_DEATH.class);
        addPacket((short) 0x8CB7, SM_PLAYER_REVIVE.class);
        addPacket((short) 0x862C, SM_PLAYER_DEATH_WINDOW.class);
        addPacket((short) 0xC99E, SM_PLAYER_GATHER_STATS.class); // OK

        // OBJECT
        addPacket((short) 0x5DB2, SM_GAMEOBJECT_SPAWN.class);
        addPacket((short) 0xC7A2, SM_GAMEOBJECT_DESPAWN.class);
        
        // CRAFT
        addPacket((short) 0x97F1, SM_CRAFT_STATS.class); // OK
        addPacket((short) 0xA3DF, SM_CRAFT_RECIPE_LIST.class); // OK

        // ABNORMALITY
        addPacket((short) 0x7486, SM_ABNORMALITY_BEGIN.class);
        addPacket((short) 0xC9A4, SM_ABNORMALITY_END.class);

        // CREATURE
        addPacket((short) 0xBD20, SM_CREATURE_HP_UPDATE.class);
        addPacket((short) 0xDD71, SM_CREATURE_UNK.class); // OK
        addPacket((short) 0xC8B0, SM_CREATURE_MP_UPDATE.class);
        addPacket((short) 0x68C2, SM_CREATURE_SPAWN.class); // OK
        addPacket((short) 0xCC31, SM_CREATURE_DESPAWN.class);
        addPacket((short) 0xAA64, SM_CREATURE_MOVE.class);
        addPacket((short) 0xEC17, SM_CREATURE_ROTATE.class);
        addPacket((short) 0xC2EA, SM_CREATURE_TARGET_PLAYER.class);
        addPacket((short) 0x95B4, SM_CREATURE_SHOW_HP.class);
        
        // DROP
        addPacket((short) 0xB741, SM_DROP_ITEM_LOOT.class);
        addPacket((short) 0xBD24, SM_DROP_ITEM_DESPAWN.class);
        addPacket((short) 0xA769, SM_DROP_ITEM_SPAWN.class);

        // DIALOG
        addPacket((short) 0x928B, SM_DIALOG.class);
        addPacket((short) 0xAE4F, SM_DIALOG_CLOSE.class);
        addPacket((short) 0xD06E, SM_DIALOG_EVENT.class);
        addPacket((short) 0xA8F5, SM_DIALOG_MENU_SELECT.class);

        // CAMPFIRE
        addPacket((short) 0xCCE4, SM_CAMPFIRE_SPAWN.class);
        addPacket((short) 0xB5EF, SM_CAMPFIRE_DESPAWN.class);

        // GROUP
        addPacket((short) 0xC689, SM_GROUP_MEMBER_LIST.class);
        addPacket((short) 0xC332, SM_GROUP_QUEST_SHARE.class);
        addPacket((short) 0xA0CB, SM_GROUP_MEMBER_STATS.class);
        addPacket((short) 0xE82F, SM_GROUP_ABNORMALS.class);
        addPacket((short) 0x7EF5, SM_GROUP_UNK.class);
        addPacket((short) 0x6DBE, SM_GROUP_MEMBER_MP_UPDATE.class);
        addPacket((short) 0x7F4D, SM_GROUP_MEMBER_MOVE.class);
        addPacket((short) 0xE874, SM_GROUP_LEAVE.class);
        addPacket((short) 0x8341, SM_GROUP_LEADER_CHANGED.class);
        addPacket((short) 0xF1E5, SM_GROUP_CONFIRM_KICK_WINDOW_SHOW.class);

        // PROFIL
        addPacket((short) 0xF8E2, SM_PLAYER_SET_TITLE.class);
        addPacket((short) 0xB8C4, SM_PLAYER_DONJON_STATS_PVP.class); // TODO

        // QUEST
        addPacket((short) 0xE3AE, SM_QUEST_CLEAR_INFO.class); // OK
        addPacket((short) 0xCCA7, SM_QUEST_INFO.class); // OK
        addPacket((short) 0xC7D9, SM_QUEST_DAILY_COMPLETE_COUNT.class); // OK
        addPacket((short) 0x95BF, SM_MISSION_COMPLETE_INFO.class); // Not sure
        addPacket((short) 0xF929, SM_QUEST_BALLOON.class);
        addPacket((short) 0x8F45, SM_QUEST_VILLAGER_INFO.class);
        addPacket((short) 0xFB81, SM_QUEST_WORLD_VILLAGER_INFO.class);
        addPacket((short) 0x5714, SM_QUEST_WORLD_VILLAGER_INFO_CLEAR.class);
        addPacket((short) 0xB433, SM_QUEST_UPDATE.class);

        // INVENTORY
        addPacket((short) 0x8034, SM_INVENTORY.class); // OK
        addPacket((short) 0x8309, SM_STORAGE.class);
        addPacket((short) 0xAA78, SM_ITEM_INFO.class);
        addPacket((short) 0xD3D7, SM_ITEM_SIMPLE_INFO.class);
        addPacket((short) 0xC8EE, SM_PLAYER_INVENTORY_SLOT_CHANGED.class);
        addPacket((short) 0x8890, SM_PLAYER_APPEARANCE_CHANGE.class); // OK
        addPacket((short) 0x5601, SM_ITEM_START_COOLTIME.class);
        addPacket((short) 0xE62F, SM_EXCHANGE_WINDOW_UPDATE.class);
        
        // TRADE
        addPacket((short) 0xE12F, SM_NPC_TRADELIST.class);
        addPacket((short) 0xCCD6, SM_TRADE_WINDOW_SHOW.class);
        
        // MAP
        addPacket((short) 0x9860, SM_MAP.class); // TODO

        // ACTIVITIES
        addPacket((short) 0xC591, SM_SOCIAL.class);

        // SOCIAL
        addPacket((short) 0xC18B, SM_PLAYER_FRIEND_LIST.class);
        addPacket((short) 0x9547, SM_PLAYER_FRIEND_ADD_SUCCESS.class);
        addPacket((short) 0x9946, SM_PLAYER_FRIEND_REMOVE_SUCCESS.class);
        addPacket((short) 0xC156, SM_REIGN_INFO.class);
        addPacket((short) 0xCAD2, SM_GUARD_PK_POLICY.class);

        // SHOP
        addPacket((short) 0xA3F4, SM_SHOP_WINDOW_OPEN.class); // TODO
        addPacket((short) 0xB79F, SM_RESPONSE_UNIQUE_OBJECT.class);
        addPacket((short) 0xC2D8, SM_RESPONSE_ACCOUNT_OBJECT.class);

        // SYSTEM
        addPacket((short) 0xDD29, SM_SYSTEM_MESSAGE.class); // OK
        addPacket((short) 0x7060, SM_WELCOME_MESSAGE.class);
        addPacket((short) 0xC563, SM_QUIT_TO_CHARACTER_LIST_WINDOW.class);
        addPacket((short) 0xC6F3, SM_QUIT_TO_CHARACTER_LIST_CANCEL.class);
        addPacket((short) 0x5140, SM_QUIT_TO_CHARACTER_LIST.class);
        addPacket((short) 0xB5E4, SM_QUIT_GAME_WINDOW.class); // OK
        addPacket((short) 0x90ED, SM_QUIT_GAME_CANCEL.class);
        addPacket((short) 0xA594, SM_QUIT_GAME.class); // OK

        // REQUEST
        addPacket((short) 0x5EEC, SM_REQUEST_CONTRACT.class);
        addPacket((short) 0xDFD7, SM_REQUEST_CONTRACT_REPLY.class);
        addPacket((short) 0xA249, SM_REQUEST_CONTRACT_CANCEL.class);
        addPacket((short) 0xAFE0, SM_REQUEST_COMPLETE.class);
        addPacket((short) 0xD2F7, SM_REQUEST_WINDOW_SHOW.class);
        addPacket((short) 0x55B9, SM_REQUEST_WINDOW_HIDE.class);

        // CHANNEL
        addPacket((short) 0xF32B, SM_PLAYER_ENTER_CHANNEL.class); // TODO
        addPacket((short) 0x907B, SM_PLAYER_CHANNEL_INFO.class); // OK
        addPacket((short) 0xA909, SM_PLAYER_CHANNEL_LIST.class);

        // GATHER
        addPacket((short) 0xB3BC, SM_GATHER_START.class);
        addPacket((short) 0xC47B, SM_GATHER_PROGRESS.class);
        addPacket((short) 0x98C0, SM_GATHER_END.class);
        addPacket((short) 0xF6F7, SM_GATHER_SPAWN.class);
        addPacket((short) 0xB9D2, SM_GATHER_DESPAWN.class);
        addPacket((short) 0x6AB5, SM_GATHERCRAFT_POINT.class); // OK

        // UNK
        addPacket((short) 0x670A, SM_PLAYER_UNK.class); // OK
        
        // CUSTOM
        addPacket((short) 0xFFFE, SM_OPCODE_LESS_PACKET.class);
        addPacket((short) 0xFFFF, KeyServerPacket.class);
    }

    @SuppressWarnings("rawtypes")
    public static Short getServerPacketId(final Class<? extends AbstractServerPacket> packetClass) {
        for (final Entry<Short, Class<? extends AbstractServerPacket<TeraGameConnection>>> entry : serverPackets.entrySet()) {
            if (entry.getValue() == packetClass) {
                return entry.getKey();
            }
        }

        log.error("Can't find packet for class " + packetClass.getName());
        return null;
    }

    private static void addPacket(final Short id, final Class<? extends AbstractServerPacket<TeraGameConnection>> packetClass) {
        if (serverPackets.containsKey(id)) {
            log.error("Server packet with id " + String.format("0x%02X: ", id) + " already exists");
            return;
        }
        serverPackets.put(id, packetClass);
    }
}
