package com.angelis.tera.game.process.model.player.quest;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javolution.util.FastList;
import javolution.util.FastMap;
import javolution.util.FastSet;

import com.angelis.tera.game.process.model.quest.Quest;
import com.angelis.tera.game.process.model.quest.QuestEnv;
import com.angelis.tera.game.process.model.quest.enums.QuestStepTypeEnum;

public class QuestList {

    public final Set<QuestEnv> questEnvs;

    public QuestList() {
        this.questEnvs = new FastSet<>();
    }
    
    public QuestList(final Set<QuestEnv> questDatas) {
        this.questEnvs = questDatas;
    }
    
    public QuestEnv addQuest(final Quest quest) {
        final QuestEnv questEnv = new QuestEnv(quest);
        questEnv.setCounters(new FastMap<Integer, Integer>());
        this.questEnvs.add(questEnv);
        
        return questEnv;
    }
    
    public void removeQuest(final Quest quest) {
        if (!this.hasQuest(quest)) {
            return;
        }
        
        final Iterator<QuestEnv> itr = this.questEnvs.iterator();
        while (itr.hasNext()) {
            final QuestEnv questEnv = itr.next();
            if (questEnv.getQuest().equals(quest)) {
                itr.remove();
                break;
            }
        }
    }
    
    public boolean hasQuest(final Quest quest) {
        boolean hasQuest = false;
        for (final QuestEnv questEnv : this.questEnvs) {
            if (questEnv.getQuest().equals(quest)) {
                hasQuest = true;
                break;
            }
        }
        return hasQuest;
    }
    
    public final Set<QuestEnv> getQuestEnvs() {
       return this.questEnvs;
    }
    
    public final Set<QuestEnv> getQuestEnvsByStepType(final QuestStepTypeEnum questStepType) {
        final Set<QuestEnv> questEnvs = new FastSet<>();
        for (final QuestEnv questEnv : this.questEnvs) {
            if (questEnv.isComplited()) {
                continue;
            }

            if (questEnv.getCurrentQuestStep().getQuestStepType() == questStepType) {
                questEnvs.add(questEnv);
            }
        }
        return questEnvs;
     }
    
    public QuestEnv getQuestEnv(final Quest quest) {
        QuestEnv questEnvForQuest = null;
        for (final QuestEnv questEnv : this.questEnvs) {
            if (questEnv.getQuest().equals(quest)) {
                questEnvForQuest = questEnv;
                break;
            }
        }
        return questEnvForQuest;
    }
    
    public QuestEnv getQuestEnv(final int questId) {
        QuestEnv questEnvForQuest = null;
        for (final QuestEnv questEnv : this.questEnvs) {
            if (questEnv.getQuest().getId() == questId) {
                questEnvForQuest = questEnv;
                break;
            }
        }
        return questEnvForQuest;
    }

    public int getQuestStep(final Quest quest) {
        int step = 0;
        for (final QuestEnv questEnv : this.questEnvs) {
            if (questEnv.getQuest().equals(quest)) {
                step = questEnv.getCurrentStep();
                break;
            }
        }
        return step;
    }

    public List<Quest> getComplitedQuest() {
        final List<Quest> quests = new FastList<>();
        for (final QuestEnv questEnv : this.questEnvs) {
            if (questEnv.isComplited()) {
                quests.add(questEnv.getQuest());
            }
        }
        return quests;
    }
    
    public boolean hasCompletedRequiredQuest(final List<Integer> requiredQuests) {
        final boolean hasCompletedQuest = true;
        for (final Integer questId : requiredQuests) {
            final QuestEnv getQuestEnv = this.getQuestEnv(questId);
            if (getQuestEnv == null) {
                return false;
            }
            
            if (!getQuestEnv.isComplited()) {
                return false;
            }
        }
        return hasCompletedQuest;
    }
}
