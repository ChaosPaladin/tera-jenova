package com.angelis.tera.game.process.model.quest;

import java.util.List;

import com.angelis.tera.game.process.model.AbstractTeraModel;
import com.angelis.tera.game.process.model.quest.enums.QuestIconTypeEnum;
import com.angelis.tera.game.process.model.quest.enums.QuestRewardTypeEnum;

public class Quest extends AbstractTeraModel {

    private QuestIconTypeEnum questType;
    private int startNpcFullId;
    private int requiredLevel;
    private List<Integer> requiredQuests;
    private List<QuestStep> questSteps;
    
    private int experienceReward;
    private int moneyReward;
    private int policyPointsReward;
    private int allianceContributionPointsReward;
    private int reputationPointsReward;
    private int creditPointsReward;
    private List<QuestReward> questRewards;
    private QuestRewardTypeEnum questRewardType;
    
    public Quest(final Integer id) {
        super(id);
    }

    public Quest() {
        super(null);
    }

    public QuestIconTypeEnum getQuestType() {
        return questType;
    }

    public void setQuestType(final QuestIconTypeEnum questType) {
        this.questType = questType;
    }

    public int getStartNpcFullId() {
        return startNpcFullId;
    }

    public void setStartNpcFullId(final int startNpcFullId) {
        this.startNpcFullId = startNpcFullId;
    }

    public int getRequiredLevel() {
        return requiredLevel;
    }

    public void setRequiredLevel(final int requiredLevel) {
        this.requiredLevel = requiredLevel;
    }

    public List<Integer> getRequiredQuests() {
        return requiredQuests;
    }

    public void setRequiredQuests(final List<Integer> requiredQuest) {
        this.requiredQuests = requiredQuest;
    }

    public List<QuestStep> getQuestSteps() {
        return questSteps;
    }

    public void setQuestSteps(final List<QuestStep> questSteps) {
        this.questSteps = questSteps;
    }
    
    public int getExperienceReward() {
        return experienceReward;
    }

    public void setExperienceReward(final int experienceReward) {
        this.experienceReward = experienceReward;
    }
    
    public int getMoneyReward() {
        return moneyReward;
    }
    
    public void setMoneyReward(final int moneyReward) {
        this.moneyReward = moneyReward;
    }
    
    public int getPolicyPointsReward() {
        return policyPointsReward;
    }
    
    public void setPolicyPointsReward(final int policyPointsReward) {
        this.policyPointsReward = policyPointsReward;
    }

    public int getAllianceContributionPointsReward() {
        return allianceContributionPointsReward;
    }
    
    public void setAllianceContributionPointsReward(final int allianceContributionPointsReward) {
        this.allianceContributionPointsReward = allianceContributionPointsReward;
    }
    
    public int getReputationPointsReward() {
        return reputationPointsReward;
    }
    
    public void setReputationPointsReward(final int reputationPointsReward) {
        this.reputationPointsReward = reputationPointsReward;
    }
    
    public int getCreditPointsReward() {
        return creditPointsReward;
    }
    
    public void setCreditPointsReward(final int creditPointsReward) {
        this.creditPointsReward = creditPointsReward;
    }

    public List<QuestReward> getQuestRewards() {
        return questRewards;
    }

    public void setQuestRewards(final List<QuestReward> questRewards) {
        this.questRewards = questRewards;
    }

    public QuestRewardTypeEnum getQuestRewardType() {
        return questRewardType;
    }

    public void setQuestRewardType(final QuestRewardTypeEnum questRewardType) {
        this.questRewardType = questRewardType;
    }

    public final int getFullId() {
        return (this.getId() * 1000);
    }
}
