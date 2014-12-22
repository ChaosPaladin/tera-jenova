package com.angelis.tera.game.presentation.network.packet;

import java.util.Map;

import javolution.util.FastMap;

import org.apache.log4j.Logger;

import com.angelis.tera.common.network.packet.AbstractClientPacket;
import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.client.*;

public class ClientPacketHandler {

    /** LOGGER */
    private static Logger log = Logger.getLogger(ClientPacketHandler.class.getName());

    private static Map<Short, Class<? extends AbstractClientPacket<TeraGameConnection>>> clientPackets = new FastMap<Short, Class<? extends AbstractClientPacket<TeraGameConnection>>>();

    public static final void init() {
        clientPackets.clear();

        // AUTH
        addPacket((short) 0x4DBC, CM_CHECK_VERSION.class); // OK
        addPacket((short) 0xB114, CM_ACCOUNT_AUTH.class); // OK
        addPacket((short) 0xA204, CM_HARDWARE_INFO.class); // OK

        // CHARACTER
        addPacket((short) 0x96F1, CM_CHARACTER_LIST.class); // OK
        addPacket((short) 0xFBC3, CM_CHARACTER_CREATE_ALLOWED.class); // OK
        addPacket((short) 0x7F90, CM_CHARACTER_CREATE_NAME_PATTERN_CHECK.class); // OK
        addPacket((short) 0xAE1B, CM_CHARACTER_CREATE_NAME_USED_CHECK.class); // OK
        addPacket((short) 0xD932, CM_CHARACTER_CREATE.class); // OK
        addPacket((short) 0xAD7C, CM_CHARACTER_DELETE.class); // OK
        addPacket((short) 0xDA2D, CM_CHARACTER_RESTORE.class); // OK

        // ENTER WORLD
        addPacket((short) 0xA5B7, CM_ENTER_WORLD.class); // OK
        addPacket((short) 0xDE8B, CM_TRADEBROKER_HIGHEST_ITEM_LEVEL.class); // OK
        addPacket((short) 0x910B, CM_LOAD_TOPO_FIN.class); // Not sure
        addPacket((short) 0x96A5, CM_UPDATE_CONTENTS_PLAYTIME.class);
        addPacket((short) 0xB135, CM_SIMPLE_TIP_REPEATED_CHECK.class); // OK
        addPacket((short) 0xD99E, CM_PLAYER_CLIMB_START.class);
        addPacket((short) 0x7957, CM_USER_SETTINGS_SAVE.class);
        addPacket((short) 0x74F3, CM_MOVIE_END.class);

        // REQUEST
        addPacket((short) 0x52DA, CM_REQUEST_CONTRACT.class);
        addPacket((short) 0xBD9A, CM_REQUEST_ACCEPT.class);
        addPacket((short) 0xC09A, CM_REQUEST_GAMESTAT_PING.class);

        // MOUNT
        addPacket((short) 0xB118, CM_PLAYER_UNMOUNT.class);

        // OPTIONS
        addPacket((short) 0xAD59, CM_OPTION_SHOW_MASK.class);
        addPacket((short) 0x6CCD, CM_OPTION_SET_VISIBILITY_DISTANCE.class); // OK

        // CHAT
        addPacket((short) 0xA951, CM_CHAT.class); // OK
        addPacket((short) 0x5F0E, CM_CHAT_INFO.class); // OK
        addPacket((short) 0xECF7, CM_WHISP.class); // OK

        // DIALOG
        addPacket((short) 0xE42C, CM_NPC_CONTACT.class);
        addPacket((short) 0xCE77, CM_DIALOG_EVENT.class);
        addPacket((short) 0xC1DC, CM_DIALOG.class);

        // ALLIANCE
        addPacket((short) 0x7E06, CM_ALLIANCE_INFO.class); // OK

        // GAMEOBJECT
        addPacket((short) 0x89F9, CM_GAMEOBJECT_REMOVE.class);

        // SKILL
        addPacket((short) 0xF8DD, CM_SKILL_START.class); // OK
        addPacket((short) 0xE29D, CM_SKILL_INSTANCE_START.class);
        addPacket((short) 0x76A8, CM_SKILL_CANCEL.class);
        addPacket((short) 0x738A, CM_GLYPH_REINIT.class);

        // PLAYER
        addPacket((short) 0x4E90, CM_PLAYER_MOVE.class); // OK
        addPacket((short) 0x6797, CM_PLAYER_ZONE_CHANGE.class); // OK
        addPacket((short) 0xAF8D, CM_LOOKING_FOR_BATTLEGROUND_WINDOW_OPEN.class); // TODO
        addPacket((short) 0x927E, CM_LOOKING_FOR_INSTANCE_WINDOW_OPEN.class); // TODO
        addPacket((short) 0xE8A2, CM_PLAYER_REPORT.class); // TODO
        addPacket((short) 0x8DA7, CM_PLAYER_COMPARE_ACHIEVEMENTS.class);
        addPacket((short) 0xB532, CM_PLAYER_INSPECT.class); // TODO
        addPacket((short) 0xA5E7, CM_PLAYER_SELECT_CREATURE.class);
        addPacket((short) 0xE195, CM_PLAYER_DONJON_CLEAR_COUNT_LIST.class);

        // GATHER
        addPacket((short) 0x885F, CM_GATHER_START.class);

        // PROFIL
        addPacket((short) 0x96A4, CM_PLAYER_SET_TITLE.class);
        addPacket((short) 0x7653, CM_PLAYER_DESCRIPTION.class);
        addPacket((short) 0x6DF5, CM_PLAYER_REINIT_INSTANCES.class);
        addPacket((short) 0xE9BC, CM_PLAYER_DONJON_STATS_PVP.class); // TODO

        // INVENTORY
        addPacket((short) 0xF9FD, CM_INVENTORY_SHOW.class); // OK
        addPacket((short) 0x7EEF, CM_ITEM_MOVE.class); // OK
        addPacket((short) 0xB139, CM_ITEM_USE.class); // OK
        addPacket((short) 0xD1F6, CM_ITEM_SIMPLE_INFO.class);
        addPacket((short) 0x83A1, CM_INVENTORY_ORDER.class); // OK
        addPacket((short) 0xB0FA, CM_PLAYER_UNEQUIP.class);
        addPacket((short) 0xF8D3, CM_PLAYER_EQUIP.class);
        addPacket((short) 0xF772, CM_PLAYER_EQUIPEMENT_ITEM_INFO.class); // OK
        addPacket((short) 0xACA1, CM_PLAYER_DUNGEON_COOLTIME_LIST.class);
        addPacket((short) 0xB106, CM_PLAYER_ITEM_TRASH.class);
        addPacket((short) 0xB663, CM_PLAYER_DROP_ITEM_PICKUP.class);

        // EXCHANGE
        addPacket((short) 0xDBD8, CM_EXCHANGE_ITEM_ADD_BUY.class);
        addPacket((short) 0xF1C5, CM_EXCHANGE_ITEM_REMOVE_BUY.class);
        addPacket((short) 0xF624, CM_EXCHANGE_ITEM_ADD_SELL.class);
        addPacket((short) 0xAE84, CM_EXCHANGE_ITEM_REMOVE_SELL.class);
        addPacket((short) 0xFBCD, CM_EXCHANGE_COMPLETE.class);
        addPacket((short) 0x8D7A, CM_EXCHANGE_CANCEL.class);

        // MAP
        addPacket((short) 0x8D02, CM_MAP_SHOW.class); // TODO

        // ACTIVITIES
        addPacket((short) 0x88B2, CM_PLAYER_EMOTE.class); // OK
        addPacket((short) 0x5761, CM_ENCHANT_WINDOW_OPEN.class);
        addPacket((short) 0xB5E3, CM_INSTANCERANK_WINDOW_OPEN.class);
        addPacket((short) 0x62EB, CM_BATTLEGROUND_WINDOW_OPEN.class);

        // GUILD
        addPacket((short) 0xF070, CM_SERVERGUILD_WINDOW_OPEN.class);
        addPacket((short) 0xACFA, CM_GUILD_INFO.class);
        addPacket((short) 0xC409, CM_GUILD_LEAVE.class);

        // SOCIAL
        addPacket((short) 0x9577, CM_PLAYER_FRIEND_LIST.class); // OK
        addPacket((short) 0xDA73, CM_PLAYER_FRIEND_ADD.class); // OK
        addPacket((short) 0xDC58, CM_PLAYER_FRIEND_REMOVE.class);
        addPacket((short) 0x63AE, CM_PLAYER_FRIEND_NOTE.class);
        addPacket((short) 0xB1BA, CM_PLAYER_BLOCK_ADD.class);
        addPacket((short) 0x50A3, CM_PLAYER_BLOCK_REMOVE.class);
        addPacket((short) 0x63B0, CM_PLAYER_BLOCK_NOTE.class);
        addPacket((short) 0xE726, CM_REIGN_INFO.class);
        addPacket((short) 0x5A75, CM_GUARD_PK_POLICY.class);

        // GROUP
        addPacket((short) 0xBD26, CM_GROUP_LEAVE.class);
        addPacket((short) 0xFDFB, CM_GROUP_KICK.class);
        addPacket((short) 0xA5E6, CM_GROUP_CONFIRM_KICK.class);
        addPacket((short) 0xDD18, CM_GROUP_CONFIRM_LEADER_CHANGE.class);
        addPacket((short) 0xA8BB, CM_GROUP_DESTROY.class);

        addPacket((short) 0x8D48, CM_PLAYER_TRADE_HISTORY.class);

        // TERA SHOP
        addPacket((short) 0xEEDF, CM_SHOP_WINDOW_OPEN.class); // OK
        addPacket((short) 0x9431, CM_REQUEST_UNIQUE_OBJECT.class);
        addPacket((short) 0xA7A9, CM_REQUEST_ACCOUNT_OBJECT.class);

        // SYSTEM
        addPacket((short) 0x74DF, CM_WELCOME_MESSAGE.class); // OK
        addPacket((short) 0xCDBD, CM_QUIT_TO_CHARACTER_LIST.class); // OK
        addPacket((short) 0xA765, CM_QUIT_TO_CHARACTER_LIST_CANCEL.class); // OK
        addPacket((short) 0xD250, CM_QUIT_GAME.class); // OK
        addPacket((short) 0xBEE0, CM_QUIT_GAME_CANCEL.class); // OK

        // PEGASUS
        addPacket((short) 0xC0E4, CM_PEGASUS_START.class);

        // CHANNEL
        addPacket((short) 0xFB75, CM_CHANNEL_LIST.class); // OK
    }

    public static Class<? extends AbstractClientPacket<TeraGameConnection>> getClientPacket(final short id) {
        final Class<? extends AbstractClientPacket<TeraGameConnection>> clientPacketClass = clientPackets.get(id);
        if (clientPacketClass == null) {
            log.error("Unknow packet with id " + String.format("0x%02X: ", id));
        }

        return clientPacketClass;
    }

    private static void addPacket(final Short id, final Class<? extends AbstractClientPacket<TeraGameConnection>> packetClass) {
        if (clientPackets.containsKey(id)) {
            log.error("Client packet with id " + String.format("0x%02X: ", id) + " already exists");
            return;
        }

        clientPackets.put(id, packetClass);
    }
}
