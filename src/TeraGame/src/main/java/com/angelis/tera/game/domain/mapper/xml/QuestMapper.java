package com.angelis.tera.game.domain.mapper.xml;

import java.util.LinkedList;
import java.util.List;

import javolution.util.FastList;

import com.angelis.tera.common.domain.mapper.xml.AbstractXMLMapper;
import com.angelis.tera.game.domain.entity.xml.quests.QuestEntity;
import com.angelis.tera.game.domain.entity.xml.quests.QuestRequiredEntity;
import com.angelis.tera.game.domain.entity.xml.quests.QuestRewardEntity;
import com.angelis.tera.game.domain.entity.xml.quests.QuestStepEntity;
import com.angelis.tera.game.domain.entity.xml.quests.QuestStepValueEntity;
import com.angelis.tera.game.process.model.quest.Quest;
import com.angelis.tera.game.process.model.quest.QuestReward;
import com.angelis.tera.game.process.model.quest.QuestStep;
import com.angelis.tera.game.process.model.quest.QuestStepValue;

public class QuestMapper extends AbstractXMLMapper<QuestEntity, Quest> {

    // ENTITY -> MODEL
    @Override
    protected Quest createNewEmptyModel() {
        return new Quest();
    }

    @Override
    public void map(final QuestEntity entity, final Quest model) {
        // DIRECT
        model.setQuestType(entity.getQuestType());
        model.setStartNpcFullId(entity.getStartNpcFullId());
        model.setRequiredLevel(entity.getRequiredLevel());
        model.setExperienceReward(entity.getExperienceReward());
        model.setMoneyReward(entity.getMoneyReward());
        model.setQuestRewardType(entity.getQuestRewardType());
        model.setPolicyPointsReward(entity.getPolicyPointsReward());
        model.setAllianceContributionPointsReward(entity.getAllianceContributionPointsReward());
        model.setReputationPointsReward(entity.getReputationPointsReward());
        model.setCreditPointsReward(entity.getCreditPointsReward());
    }

    @Override
    protected void finalizeDependencies(final QuestEntity entity, final Quest model) {
        // REQUIRED QUESTS
        final List<Integer> requiredQuests = new FastList<>();
        if (entity.getRequiredQuests() != null && !entity.getRequiredQuests().isEmpty()) {
            for (final QuestRequiredEntity questRequiredEntity : entity.getRequiredQuests()) {
                requiredQuests.add(questRequiredEntity.getQuestId());
            }
        }
        model.setRequiredQuests(requiredQuests);

        // STEPS
        final List<QuestStep> questSteps = new LinkedList<>();
        for (final QuestStepEntity questStepEntity : entity.getQuestSteps()) {
            final List<QuestStepValue> questStepValues = new FastList<>(questStepEntity.getStepValues().size());
            for (final QuestStepValueEntity questStepValueEntity : questStepEntity.getStepValues()) {
                questStepValues.add(new QuestStepValue(questStepValueEntity.getObjectId(), questStepValueEntity.getAmount()));
            }
            questSteps.add(new QuestStep(questStepEntity.getQuestStepType(), questStepValues));
        }
        model.setQuestSteps(questSteps);

        final List<QuestReward> questRewards = new FastList<>();
        if (entity.getQuestRewards() != null && !entity.getQuestRewards().isEmpty()) {
            for (final QuestRewardEntity questRewardEntity : entity.getQuestRewards()) {
                final QuestReward questReward = new QuestReward();
                questReward.setItemId(questRewardEntity.getItemId());
                questReward.setAmount(questRewardEntity.getAmount());
                questRewards.add(questReward);
            }
        }
        model.setQuestRewards(questRewards);
    }

}
