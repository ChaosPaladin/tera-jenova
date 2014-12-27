package com.angelis.tera.game.process.services;

import java.util.List;
import java.util.Map;

import javolution.util.FastList;
import javolution.util.FastMap;

import org.apache.log4j.Logger;

import com.angelis.tera.common.domain.mapper.MapperManager;
import com.angelis.tera.common.services.AbstractService;
import com.angelis.tera.common.utils.CollectionUtils;
import com.angelis.tera.common.utils.Rnd;
import com.angelis.tera.game.config.QuestConfig;
import com.angelis.tera.game.domain.entity.xml.quests.QuestEntity;
import com.angelis.tera.game.domain.entity.xml.quests.QuestEntityHolder;
import com.angelis.tera.game.domain.mapper.xml.QuestMapper;
import com.angelis.tera.game.presentation.network.SystemMessages;
import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;
import com.angelis.tera.game.presentation.network.packet.server.SM_MISSION_COMPLETE_INFO;
import com.angelis.tera.game.presentation.network.packet.server.SM_OPCODE_LESS_PACKET;
import com.angelis.tera.game.presentation.network.packet.server.SM_QUEST_BALLOON;
import com.angelis.tera.game.presentation.network.packet.server.SM_QUEST_CLEAR_INFO;
import com.angelis.tera.game.presentation.network.packet.server.SM_QUEST_DAILY_COMPLETE_COUNT;
import com.angelis.tera.game.presentation.network.packet.server.SM_QUEST_INFO;
import com.angelis.tera.game.process.model.creature.Creature;
import com.angelis.tera.game.process.model.creature.Npc;
import com.angelis.tera.game.process.model.dialog.Dialog;
import com.angelis.tera.game.process.model.dialog.DialogButton;
import com.angelis.tera.game.process.model.dialog.actions.quest.QuestAcceptDialogAction;
import com.angelis.tera.game.process.model.dialog.actions.quest.QuestCompliteDialogAction;
import com.angelis.tera.game.process.model.dialog.actions.quest.QuestReadDialogAction;
import com.angelis.tera.game.process.model.dialog.actions.quest.QuestUpdateDialogAction;
import com.angelis.tera.game.process.model.dialog.enums.DialogIconEnum;
import com.angelis.tera.game.process.model.drop.Drop;
import com.angelis.tera.game.process.model.drop.enums.ItemCategoryEnum;
import com.angelis.tera.game.process.model.enums.StorageTypeEnum;
import com.angelis.tera.game.process.model.item.Item;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.model.player.quest.QuestList;
import com.angelis.tera.game.process.model.quest.Quest;
import com.angelis.tera.game.process.model.quest.QuestEnv;
import com.angelis.tera.game.process.model.quest.QuestReward;
import com.angelis.tera.game.process.model.quest.QuestStep;
import com.angelis.tera.game.process.model.quest.enums.QuestStepTypeEnum;
import com.angelis.tera.game.utils.QuestUtils;

public class QuestService extends AbstractService {

    /** LOGGER */
    private static Logger log = Logger.getLogger(QuestService.class.getName());

    private final Map<Integer, List<Quest>> quests = new FastMap<>();

    private QuestService() {
    }

    @Override
    public void onInit() {
        for (final QuestEntity questEntity : XMLService.getInstance().getEntity(QuestEntityHolder.class).getQuests()) {
            CollectionUtils.addToMapOfList(quests, questEntity.getStartNpcFullId(), MapperManager.getXMLMapper(QuestMapper.class).map(questEntity));
        }
        XMLService.getInstance().clearEntity(QuestEntityHolder.class);

        log.info("QuestService started");
    }

    @Override
    public void onDestroy() {
        quests.clear();
        log.info("QuestService stopped");
    }

    public void onPlayerCreate(final Player player) {
        player.setQuestList(new QuestList());
    }

    public void onPlayerEnterWorld(final Player player) {
        final TeraGameConnection connection = player.getConnection();
        
        connection.sendPacket(new SM_QUEST_CLEAR_INFO());
        for (final QuestEnv questEnv : player.getQuestList().getQuestEnvs()) {
            connection.sendPacket(new SM_QUEST_INFO(questEnv, new FastList<Npc>(), false));
        }
        
        connection.sendPacket(new SM_QUEST_DAILY_COMPLETE_COUNT(player));
        connection.sendPacket(new SM_MISSION_COMPLETE_INFO());
    }

    public void onPlayerLevelUp(final Player player) {
        for (final List<Quest> quests : this.quests.values()) {
            for (final Quest quest : quests) {
                if (quest.getRequiredLevel() < player.getLevel() && QuestUtils.checkRequirements(player, quest)) {
                    this.onPlayerStartQuest(player, null, quest);
                }
            }
        }
    }
    
    public void onPlayerItemPickup(final Player player, final Item item) {
        for (final QuestEnv questEnv : player.getQuestList().getQuestEnvsByStepType(QuestStepTypeEnum.COLLECT)) {
            final QuestStep currentQuestStep = questEnv.getCurrentQuestStep();
            final int fullId = item.getId();
            if (currentQuestStep.containsObjectId(fullId)) {
                if (currentQuestStep.getRequiredObjectAmountByObjectId(fullId) < questEnv.getCounters().get(fullId)) {
                    questEnv.addCounter(fullId);
                }
            }
        }

        QuestUtils.checkQuestStates(player);
    }
    
    public void onPlayerCreatureKill(final Player player, final Creature killed) {
        final int fullId = killed.getTemplate().getFullId();

        for (final QuestEnv questEnv : player.getQuestList().getQuestEnvsByStepType(QuestStepTypeEnum.KILL)) {
            final QuestStep currentQuestStep = questEnv.getCurrentQuestStep();
            
            // KILL
            if (currentQuestStep.containsObjectId(fullId)) {
                Integer killedAmount = questEnv.getCounters().get(fullId);
                if (killedAmount == null) {
                    killedAmount = 0;
                }

                if (currentQuestStep.getRequiredObjectAmountByObjectId(fullId) < killedAmount) {
                    questEnv.addCounter(fullId);
                }
            }
            
            // AUTOLOOT ON KILL
            for (final Drop questDrop : DropService.getInstance().getDropFromCreature(killed, ItemCategoryEnum.QUEST)) {
                if (currentQuestStep.containsObjectId(questDrop.getItemId())) {
                    float chance = questDrop.getDropChance().value;
                    if (chance < 100) {
                        chance = Rnd.get(0, 100);
                    }
                    
                    if (Rnd.chance(chance)) {
                        StorageService.getInstance().addItem(player, player.getStorage(StorageTypeEnum.INVENTORY), questDrop.getItemId(), 1);
                    }
                }
            }
        }

        QuestUtils.checkQuestStates(player);
    }

    public void onPlayerProgressQuest(final Player player, final QuestEnv questEnv) {
        // TODO
        questEnv.addStep();
        QuestUtils.checkQuestStates(player);
    }

    public void onPlayerStartQuest(final Player player, final Npc npc, final Quest quest) {
        final QuestList questList = player.getQuestList();
        if (questList.hasQuest(quest)) {
            return;
        }

        final QuestEnv questEnv = questList.addQuest(quest);

        final List<TeraServerPacket> packets = new FastList<>();
        QuestUtils.updatePlayerQuestNpcVillageInfo(player, packets);

        packets.add(new SM_OPCODE_LESS_PACKET("3EE515050000D3A0860401"));
        packets.add(new SM_QUEST_INFO(questEnv, new FastList<Npc>(), false));
        packets.add(new SM_QUEST_BALLOON(npc, quest));
        packets.add(SystemMessages.QUEST_STARTED(String.valueOf((quest.getId()*1000)+1)));

        player.getConnection().sendPackets(packets);
        
        // Check if this quest is not already complete :)
        QuestUtils.checkQuestStates(player);
    }
    
    public void onPlayerCompliteQuest(final Player player, final Quest quest) {
        final QuestEnv questEnv = player.getQuestList().getQuestEnv(quest);
        if (questEnv == null) {
            return;
        }
        
        questEnv.setComplited(true);
        PlayerService.getInstance().onPlayerUpdateExperience(player, null, quest.getExperienceReward()*QuestConfig.QUEST_EXPERIENCE_RATE);
        StorageService.getInstance().addItem(player, player.getStorage(StorageTypeEnum.INVENTORY), Item.MONEY_ID, quest.getMoneyReward()*QuestConfig.QUEST_MONEY_RATE);

        for (final QuestReward questReward : quest.getQuestRewards()) {
            StorageService.getInstance().addItem(player, player.getStorage(StorageTypeEnum.INVENTORY), questReward.getItemId(), questReward.getAmount());
        }
        
        final List<TeraServerPacket> packets = new FastList<>();
        QuestUtils.updatePlayerQuestNpcVillageInfo(player, packets);
        packets.add(SystemMessages.QUEST_COMPLITED(String.valueOf((quest.getId()*1000)+1)));
        player.getConnection().sendPackets(packets);
    }

    public void onPlayerCancelQuest(final Player player, final Quest quest) {
        player.getQuestList().removeQuest(quest);

        final List<TeraServerPacket> packets = new FastList<>();
        QuestUtils.updatePlayerQuestNpcVillageInfo(player, packets);
        player.getConnection().sendPackets(packets);
    }
    
    public void onPlayerReadQuestDialog(final Player player, final Npc npc, final Quest quest) {
        if (player.getController().getDialog() == null) {
            return;
        }
        
        final Dialog dialog = new Dialog(player, npc);
        dialog.setQuest(quest);
        dialog.setPage(player.getController().getDialog().getPage()+1);
        
        final QuestEnv questEnv = player.getQuestList().getQuestEnv(quest);
        if (questEnv == null) {
            dialog.addDialogButton(new DialogButton(dialog, DialogIconEnum.DEFAULT_QUEST, "@quest:1", new QuestAcceptDialogAction(player, dialog, quest)));
        } else if (!questEnv.isCurrentQuestStepLast()) {
            dialog.addPage();
            dialog.addDialogButton(new DialogButton(dialog, DialogIconEnum.DEFAULT_QUEST, "@quest:"+(quest.getFullId()+dialog.getPage()), new QuestUpdateDialogAction(player, dialog, quest)));
            this.onPlayerProgressQuest(player, questEnv);
        } else {
            dialog.addDialogButton(new DialogButton(dialog, DialogIconEnum.DEFAULT_QUEST, "@quest:"+(quest.getFullId()+4), new QuestCompliteDialogAction(player, dialog, quest)));
        }

        final List<TeraServerPacket> packets = new FastList<>();
        QuestUtils.updatePlayerQuestNpcVillageInfo(player, packets);
        player.getConnection().sendPackets(packets);
        DialogService.getInstance().sendDialogToPlayer(player, dialog);
    }
    
    public void onPlayerZoneChange(final Player player, final byte[] datas) {
        // TODO Auto-generated method stub
        
    }

    public void addQuestDialogButtons(final Npc npc, final Player player, final Dialog dialog) {
        final List<Quest> quests = QuestService.getInstance().getQuestsByCreature(npc);

        // Start quest
        if (quests != null && !quests.isEmpty()) {
            for (final Quest quest : quests) {
                if (!QuestUtils.checkRequirements(player, quest)) {
                    continue;
                }

                final DialogIconEnum dialogIcon = QuestUtils.getDialogIconEnum(quest, null);
                dialog.addDialogButton(new DialogButton(dialog, dialogIcon, "@quest:" + (quest.getFullId()+1), new QuestReadDialogAction(player, dialog, quest)));
            }
        }

        // Update quest
        for (final QuestEnv questEnv : player.getQuestList().getQuestEnvs()) {
            if (questEnv.isComplited()) {
                continue;
            }

            final QuestStep questStep = questEnv.getCurrentQuestStep();
            if (questStep.getQuestStepType() == QuestStepTypeEnum.TALK) {
                final Quest quest = questEnv.getQuest();
                if (questStep.containsObjectId(npc.getTemplate().getFullId())) {
                    final DialogIconEnum dialogIcon = QuestUtils.getDialogIconEnum(quest, questEnv);
                    dialog.addDialogButton(new DialogButton(dialog, dialogIcon, "@quest:" + (quest.getFullId()+1), new QuestReadDialogAction(player, dialog, quest)));
                }
            }
        }
    }

    public final List<Quest> getQuestsByCreature(final Npc npc) {
        return quests.get(npc.getTemplate().getFullId());
    }

    public final Quest getQuestById(final int questId) {
        for (final List<Quest> quests : this.quests.values()) {
            for (final Quest quest : quests) {
                if (quest.getId() == questId) {
                    return quest;
                }
            }
        }
        return null;
    }

    /** SINGLETON */
    public static QuestService getInstance() {
        return SingletonHolder.instance;
    }
    
    private static class SingletonHolder {
        protected static final QuestService instance = new QuestService();
    }
}
