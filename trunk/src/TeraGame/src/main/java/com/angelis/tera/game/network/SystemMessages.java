package com.angelis.tera.game.network;

import com.angelis.tera.game.network.packet.server.SM_SYSTEM_MESSAGE;

public class SystemMessages {

    // PLAYER
    public static final SM_SYSTEM_MESSAGE CONGRATULATION_YOU_ARE_LEVEL(final String level) {
        return new SM_SYSTEM_MESSAGE(new String[] { "@24", "level", level });
    }
    public static final SM_SYSTEM_MESSAGE YOU_HAVE_POOR_STAMINA() {
        return new SM_SYSTEM_MESSAGE(new String[] { "@635" });
    }
    public static final SM_SYSTEM_MESSAGE YOU_HAVE_ABUNDANT_STAMINA() {
        return new SM_SYSTEM_MESSAGE(new String[] { "@636" });
    }
    public static SM_SYSTEM_MESSAGE NO_INSTANCE_TO_REINIT() {
        return new SM_SYSTEM_MESSAGE(new String[] { "@1269" });
    }
    public static SM_SYSTEM_MESSAGE FALL_DAMAGE(final String damage) {
        return new SM_SYSTEM_MESSAGE(new String[] { "@675", "Damage", damage });
    }
    public static SM_SYSTEM_MESSAGE YOU_ARE_DEAD(final String playerName) {
        return new SM_SYSTEM_MESSAGE(new String[] { "@655", "UserName", playerName });
    }

    // STORAGE
    public static SM_SYSTEM_MESSAGE YOU_CANT_UPGRADE_YOUR_STORAGE() {
        return new SM_SYSTEM_MESSAGE(new String[] { "@1559" });
    }
    
    // CHAT
    public static final SM_SYSTEM_MESSAGE WHISP_INVALID_TARGET() {
        return new SM_SYSTEM_MESSAGE(new String[] { "@111" });
    }
    public static final SM_SYSTEM_MESSAGE WHISP_PLAYER_NOT_ONLINE() {
        return new SM_SYSTEM_MESSAGE(new String[] { "@113" });
    }

    // MAILBOX
    public static final SM_SYSTEM_MESSAGE MAILBOX_MESSAGE_SENT() {
        return new SM_SYSTEM_MESSAGE(new String[] { "@444" });
    }
    public static final SM_SYSTEM_MESSAGE MAILBOX_TARGET_INVALID() {
        return new SM_SYSTEM_MESSAGE(new String[] { "@724" });
    }
    
    // GLOBAL CHAT
    public static final SM_SYSTEM_MESSAGE CHARACTER_MUST_BE_LEVEL_TO_USE_THIS_CHANNEL(final String level) {
        return new SM_SYSTEM_MESSAGE(new String[] { "@1946", "Level", level });
    }

    // WHISP
    public static final SM_SYSTEM_MESSAGE CHARACTER_IGNORE_YOUR_WHISP(final String playerName) {
        return new SM_SYSTEM_MESSAGE(new String[] { "@1338", "UserName", playerName });
    }

    // QUEST
    public static final SM_SYSTEM_MESSAGE QUEST_STARTED(final String questDialogId) {
        return new SM_SYSTEM_MESSAGE(new String[] { "@624", "QuestName", "@quest:"+questDialogId });
    }
    public static final SM_SYSTEM_MESSAGE QUEST_COMPLITED(final String questDialogId) {
        return new SM_SYSTEM_MESSAGE(new String[] { "@1511", "QuestName", "@quest:"+questDialogId });
    }
    
    // CAMPFIRE
    public static final SM_SYSTEM_MESSAGE YOU_CANT_MAKE_FIRECAMP_RIGHT_NOW() {
        return new SM_SYSTEM_MESSAGE(new String[] { "@628" });
    }
    public static final SM_SYSTEM_MESSAGE THERE_IS_ANOTHER_FIRECAMP_NEAR_HERE() {
        return new SM_SYSTEM_MESSAGE(new String[] { "@629" });
    }
    public static SM_SYSTEM_MESSAGE TOO_MANY_ITEMS_ARE_BEING_USED_IN_THIS_CAMPFIRE_TRY_AGAIN_LATER() {
        return new SM_SYSTEM_MESSAGE(new String[] { "@630" });
    }
    public static SM_SYSTEM_MESSAGE CHARM_CAN_ONLY_BE_USED_NEAR_CAMPFIRE() {
        return new SM_SYSTEM_MESSAGE(new String[] { "@631" });
    }
    public static final SM_SYSTEM_MESSAGE YOU_ARE_CHARGING_STAMINA() {
        return new SM_SYSTEM_MESSAGE(new String[] { "@632" });
    }
    public static final SM_SYSTEM_MESSAGE YOU_ARE_NO_LONGER_CHARGING_STAMINA() {
        return new SM_SYSTEM_MESSAGE(new String[] { "@633" });
    }

    // FRIEND
    public static final SM_SYSTEM_MESSAGE FRIEND_ADD_UNKNOWN_TARGET() {
        return new SM_SYSTEM_MESSAGE(new String[] { "@430" });
    }
    public static final SM_SYSTEM_MESSAGE FRIEND_ADD_SUCCESS(final String playerName) {
        return new SM_SYSTEM_MESSAGE(new String[] { "@432", "UserName", playerName });
    }
    public static final SM_SYSTEM_MESSAGE FRIEND_ADDED_YOU(final String playerName) {
        return new SM_SYSTEM_MESSAGE(new String[] { "@433", "UserName", playerName });
    }
    public static final SM_SYSTEM_MESSAGE FRIEND_MAX_ADD_REQUEST_IS_3_PER_DAY() {
        return new SM_SYSTEM_MESSAGE(new String[] { "@434" });
    }
    public static final SM_SYSTEM_MESSAGE FRIEND_REMOVE_SUCCESS(final String playerName) {
        return new SM_SYSTEM_MESSAGE(new String[] { "@436", "UserName", playerName });
    }
    public static final SM_SYSTEM_MESSAGE FRIEND_REMOVED_YOU(final String playerName) {
        return new SM_SYSTEM_MESSAGE(new String[] { "@437", "UserName", playerName }); // NOT SURE
    }
    public static final SM_SYSTEM_MESSAGE FRIEND_ADD_INVALID_TARGET() {
        return new SM_SYSTEM_MESSAGE(new String[] { "@441" });
    }
    
    // GROUP
    public static final SM_SYSTEM_MESSAGE GROUP_MEMBER_IS_DEAD(final String partyPlayerName) {
        return new SM_SYSTEM_MESSAGE(new String[] { "@602", "PartyPlayerName", partyPlayerName });
    }
    public static final SM_SYSTEM_MESSAGE GROUP_MEMBER_HAS_REVIVE(final String partyPlayerName) {
        return new SM_SYSTEM_MESSAGE(new String[] { "@603", "PartyPlayerName", partyPlayerName });
    }
    public static final SM_SYSTEM_MESSAGE GROUP_HAS_BEEN_DESTROYED() {
        return new SM_SYSTEM_MESSAGE(new String[] { "@193" });
    }
    public static final SM_SYSTEM_MESSAGE GROUP_CONFIRM_KICK(final String partyPlayerName) {
        return new SM_SYSTEM_MESSAGE(new String[] { "@1893", "PartyPlayerName", partyPlayerName });
    }
    public static final SM_SYSTEM_MESSAGE GROUP_REFUSED_KICK(final String partyPlayerName) {
        return new SM_SYSTEM_MESSAGE(new String[] { "@1894", "PartyPlayerName", partyPlayerName });
    }
    public static final SM_SYSTEM_MESSAGE GROUP_IT_IS_TOO_EARLY_TO_START_ANOTHER_VOTE() {
        return new SM_SYSTEM_MESSAGE(new String[] { "@2503" });
    }
    
    // PLAYER_TRADE
    public static final SM_SYSTEM_MESSAGE PLAYER_TRADE_START() {
        return new SM_SYSTEM_MESSAGE(new String[] { "@356" });
    }
    
    // ACCOUNT
    public static final SM_SYSTEM_MESSAGE ACCOUNT_BENEFIT(final String benefit) {
        return new SM_SYSTEM_MESSAGE(new String[] { "@2694", "AccountBenefitName", "@accountBenefit:"+benefit });
    }
    
    // LOADING SCREEN
    public static final SM_SYSTEM_MESSAGE YOU_CAN_JOIN_GUILD_VIA_SOCIAL_LINK() {
        return new SM_SYSTEM_MESSAGE(new String[] { "@2812" });
    }
    
    // GUILD
    public static final SM_SYSTEM_MESSAGE YOU_CANT_AFFECT_EXECUTIVE_MEMBER_AS_CHIEF() {
        return new SM_SYSTEM_MESSAGE(new String[] { "@2810" });
    }
    
    // TERA
    public static final SM_SYSTEM_MESSAGE CONNECT_ON_TERA_EUROPE_FOR_LAST_NEWS() {
        return new SM_SYSTEM_MESSAGE(new String[] { "@888" });
    }

    // TEAM
    public static final SM_SYSTEM_MESSAGE PLAYER_JOIN_THE_TEAM(final String playerName) {
        return new SM_SYSTEM_MESSAGE(new String[] { "@151", "player", playerName });
    }
    public static final SM_SYSTEM_MESSAGE PLAYER_LEFT_THE_TEAM(final String playerName) {
        return new SM_SYSTEM_MESSAGE(new String[] { "@152", "player", playerName });
    }
    
    // REQUEST
    public static final SM_SYSTEM_MESSAGE REQUEST_PLAYER_NOT_FOUND() {
        return new SM_SYSTEM_MESSAGE(new String[] { "@2090" });
    }
    public static SM_SYSTEM_MESSAGE REQUEST_CHOOSE_ANOTHER_PLAYER() {
        return new SM_SYSTEM_MESSAGE(new String[] { "@332" });
    }
    
    // BATTLE
    public static final SM_SYSTEM_MESSAGE BATTLE_START() {
        return new SM_SYSTEM_MESSAGE(new String[] { "@600" });
    }
    public static final SM_SYSTEM_MESSAGE BATTLE_END() {
        return new SM_SYSTEM_MESSAGE(new String[] { "@601" });
    }
    public static SM_SYSTEM_MESSAGE SKILL_WEAPON_IS_REQUIRED() {
        return new SM_SYSTEM_MESSAGE(new String[] { "@38" });
    }

    // CRAFT
    public static SM_SYSTEM_MESSAGE YOUR_MINING_HAS_INCREASED_TO(final String level) {
        return new SM_SYSTEM_MESSAGE(new String[] { "@67", "prof", level });
    }
    public static SM_SYSTEM_MESSAGE YOUR_PLANT_COLLECTING_HAS_INCREASED_TO(final String level) {
        return new SM_SYSTEM_MESSAGE(new String[] { "@68", "prof", level });
    }
    public static SM_SYSTEM_MESSAGE YOUR_BUG_HUNTING_HAS_INCREASED_TO(final String level) {
        return new SM_SYSTEM_MESSAGE(new String[] { "@69", "prof", level });
    }
    public static SM_SYSTEM_MESSAGE YOUR_ESSENCE_GATHERING_HAS_INCREASED_TO(final String level) {
        return new SM_SYSTEM_MESSAGE(new String[] { "@70", "prof", level });
    }
    
    // ITEM
    public static SM_SYSTEM_MESSAGE THAT_IS_NOT_YOURS() {
        return new SM_SYSTEM_MESSAGE(new String[]{ "@41" });
    }
    public static SM_SYSTEM_MESSAGE INVENTORY_IS_FULL() {
        return new SM_SYSTEM_MESSAGE(new String[]{ "@39" });
    }
    public static SM_SYSTEM_MESSAGE ITEM_NEW_AMOUNT(final String itemId, final String itemAmount) {
        return new SM_SYSTEM_MESSAGE(new String[] { "@345", "ItemName", "@item:"+itemId, "ItemAmount", itemAmount });
    }
    public static SM_SYSTEM_MESSAGE ITEM_ADD(final String username, final String itemId, final String itemAmount) {
        return new SM_SYSTEM_MESSAGE(new String[] { "@379", "UserName", username, "ItemAmount", itemAmount, "ItemName", "@item:"+itemId });
    }
    public static SM_SYSTEM_MESSAGE MONEY_ADD(final String moneyAmount) {
        return new SM_SYSTEM_MESSAGE(new String[] { "@380", "Money", moneyAmount });
    }
    public static SM_SYSTEM_MESSAGE AN_ITEM_IS_AWAITING_IN_CLAME() {
        return new SM_SYSTEM_MESSAGE(new String[] { "@1514" });
    }
    public static SM_SYSTEM_MESSAGE ITEM_COOLDOWN(final String itemId) {
        return new SM_SYSTEM_MESSAGE(new String[] { "@1767", "ItemName", "@item:"+itemId });
    }

    // SKILL
    public static SM_SYSTEM_MESSAGE YOU_CANNOT_USE_THAT_SKILL_AT_THE_MOMENT() {
        return new SM_SYSTEM_MESSAGE(new String[] { "@36" });
    }
    public static SM_SYSTEM_MESSAGE YOU_HAVE_LEARNED_SKILL(final String skillId, final String todo) {
        return new SM_SYSTEM_MESSAGE(new String[] { "@650", "SkillName", "@Skill:"+skillId+"#"+todo });
    }

    // CUSTOM
    public static final SM_SYSTEM_MESSAGE CUSTOM(final String[] values) {
        return new SM_SYSTEM_MESSAGE(values);
    }
}
