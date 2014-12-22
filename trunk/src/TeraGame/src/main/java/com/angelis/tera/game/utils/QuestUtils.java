package com.angelis.tera.game.utils;

import java.util.List;

import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;
import com.angelis.tera.game.presentation.network.packet.server.SM_QUEST_VILLAGER_INFO;
import com.angelis.tera.game.presentation.network.packet.server.SM_QUEST_WORLD_VILLAGER_INFO_CLEAR;
import com.angelis.tera.game.process.model.creature.Creature;
import com.angelis.tera.game.process.model.creature.Npc;
import com.angelis.tera.game.process.model.dialog.enums.DialogIconEnum;
import com.angelis.tera.game.process.model.enums.StorageTypeEnum;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.model.player.quest.QuestList;
import com.angelis.tera.game.process.model.quest.Quest;
import com.angelis.tera.game.process.model.quest.QuestEnv;
import com.angelis.tera.game.process.model.quest.QuestStepValue;
import com.angelis.tera.game.process.model.quest.enums.QuestNpcIconEnum;
import com.angelis.tera.game.process.model.quest.enums.QuestStepTypeEnum;
import com.angelis.tera.game.process.model.storage.Storage;
import com.angelis.tera.game.process.model.visible.VisibleTeraObject;
import com.angelis.tera.game.process.model.visible.enums.VisibleTypeEnum;
import com.angelis.tera.game.process.services.QuestService;

public class QuestUtils {

    public static final QuestNpcIconEnum getQuestNpcIconEnum(final Quest quest, final QuestEnv questEnv) {
        QuestNpcIconEnum questNpcIconEnum = null;
        switch (quest.getQuestType()) {
            case STORY:
                if (questEnv == null) {
                    questNpcIconEnum = QuestNpcIconEnum.STORY_QUEST_AVAILABLE;
                } else if (!questEnv.isCurrentQuestStepLast()) {
                    questNpcIconEnum = QuestNpcIconEnum.STORY_QUEST_PROCESS;
                } else {
                    questNpcIconEnum = QuestNpcIconEnum.STORY_QUEST_REWARD;
                }
            break;

            case STORY_REPEATABLE:
                if (questEnv == null) {
                    questNpcIconEnum = QuestNpcIconEnum.STORY_REPEATABLE_QUEST_AVAILABLE;
                } else if (!questEnv.isCurrentQuestStepLast()) {
                    questNpcIconEnum = QuestNpcIconEnum.STORY_REPEATABLE_QUEST_PROCESS;
                }
            break;

            case NORMAL:
                if (questEnv == null) {
                    questNpcIconEnum = QuestNpcIconEnum.NORMAL_QUEST_AVAILABLE;
                } else if (!questEnv.isCurrentQuestStepLast()) {
                    questNpcIconEnum = QuestNpcIconEnum.NORMAL_QUEST_PROCESS;
                } else {
                    questNpcIconEnum = QuestNpcIconEnum.NORMAL_QUEST_REWARD;
                }
            break;

            case NORMAL_REPEATABLE:
                if (questEnv == null) {
                    questNpcIconEnum = QuestNpcIconEnum.NORMAL_REPEATABLE_QUEST_AVAILABLE;
                } else if (!questEnv.isCurrentQuestStepLast()) {
                    questNpcIconEnum = QuestNpcIconEnum.NORMAL_REPEATABLE_QUEST_PROCESS;
                }
            break;

            default:
                questNpcIconEnum = QuestNpcIconEnum.NONE;
        }

        return questNpcIconEnum;
    }

    public static final DialogIconEnum getDialogIconEnum(final Quest quest, final QuestEnv questEnv) {
        DialogIconEnum dialogIcon = null;
        switch (quest.getQuestType()) {
            case STORY:
                if (questEnv == null) {
                    dialogIcon = DialogIconEnum.STORY_QUEST_START;
                } else if (!questEnv.isCurrentQuestStepLast()) {
                    dialogIcon = DialogIconEnum.STORY_QUEST_PROGRESS;
                } else {
                    dialogIcon = DialogIconEnum.STORY_QUEST_REWARD;
                }
            break;
            
            case STORY_REPEATABLE:
                if (questEnv == null) {
                    dialogIcon = DialogIconEnum.STORY_REPEATABLE_QUEST_START;
                } else if (!questEnv.isCurrentQuestStepLast()) {
                    dialogIcon = DialogIconEnum.STORY_REPEATABLE_QUEST_PROGRESS;
                }
            break;

            case NORMAL:
                if (questEnv == null) {
                    dialogIcon = DialogIconEnum.NORMAL_QUEST_START;
                } else if (!questEnv.isCurrentQuestStepLast()) {
                    dialogIcon = DialogIconEnum.NORMAL_QUEST_PROGRESS;
                } else {
                    dialogIcon = DialogIconEnum.NORMAL_QUEST_REWARD;
                }
            break;

            case NORMAL_REPEATABLE:
                if (questEnv == null) {
                    dialogIcon = DialogIconEnum.NORMAL_REPEATABLE_QUEST_START;
                } else if (!questEnv.isCurrentQuestStepLast()) {
                    dialogIcon = DialogIconEnum.NORMAL_REPEATABLE_QUEST_PROGRESS;
                }
            break;

            default:
                dialogIcon = DialogIconEnum.STORY_QUEST_START;
        }

        return dialogIcon;
    }
    
    public static final boolean checkRequirements(final Player player, final Quest quest) {
        if (player.getLevel() < quest.getRequiredLevel()) {
            return false;
        }

        final QuestList questList = player.getQuestList();
        if (questList.hasQuest(quest)) {
            return false;
        }
        
        if (!questList.hasCompletedRequiredQuest(quest.getRequiredQuests())) {
            return false;
        }
        
        return true;
    }
    
    public static final void updatePlayerQuestNpcVillageInfo(final Player player, final List<TeraServerPacket> packets, final Creature teraCreature) {
        if (teraCreature instanceof Npc) {
            boolean hasQuest = false;

            final Npc npc = (Npc) teraCreature;
            final List<Quest> quests = QuestService.getInstance().getQuestsByCreature(npc);
            
            // Player quest
            for (final QuestEnv questEnv : player.getQuestList().getQuestEnvs()) {
                if (questEnv.isComplited()) {
                    continue;
                }

                final Quest quest = questEnv.getQuest();
                if (questEnv.getCurrentQuestStep().containsObjectId(teraCreature.getTemplate().getFullId())) {
                    hasQuest = true;
                    packets.add(new SM_QUEST_VILLAGER_INFO(teraCreature, QuestUtils.getQuestNpcIconEnum(quest, questEnv)));
                }
            }
            
            if (!hasQuest) {
                // new quest
                if (quests != null && !quests.isEmpty()) {
                    for (final Quest quest : quests) {
                        if (!QuestUtils.checkRequirements(player, quest)) {
                            continue;
                        }
    
                        hasQuest = true;
                        packets.add(new SM_QUEST_VILLAGER_INFO(teraCreature, QuestUtils.getQuestNpcIconEnum(quest, null)));
                    }
                }
            }

            if (!hasQuest) {
                packets.add(new SM_QUEST_VILLAGER_INFO(teraCreature, QuestNpcIconEnum.NONE));
            }
        }
    }
    
    public static final void updatePlayerQuestNpcVillageInfo(final Player player, final List<TeraServerPacket> packets) {
        packets.add(new SM_QUEST_WORLD_VILLAGER_INFO_CLEAR());

        for (final VisibleTeraObject visibleNpc : player.getKnownList().getVisible(VisibleTypeEnum.NPC)) {
            final Npc npc = (Npc) visibleNpc;
            QuestUtils.updatePlayerQuestNpcVillageInfo(player, packets, npc);
        }
    }
    
    public static final void checkQuestStates(final Player player) {
        for (final QuestEnv questEnv : player.getQuestList().getQuestEnvs()) {
            if (questEnv.isComplited()) {
                continue;
            }

            if (questEnv.getCurrentQuestStep().getQuestStepType() == QuestStepTypeEnum.COLLECT) {
                final Storage inventory = player.getStorage(StorageTypeEnum.INVENTORY);
                for (final QuestStepValue stepValue : questEnv.getCurrentQuestStep().getQuestStepValues()) {
                    if (inventory.getStorageItemByItemId(stepValue.getObjectId()).getCount() >= stepValue.getAmount()) {
                        questEnv.setCounter(stepValue.getObjectId(), stepValue.getAmount());
                    }
                }
            }

            if (questEnv.hasAllCounters()) {
                QuestService.getInstance().onPlayerProgressQuest(player, questEnv);
            }
        }
    }
}
