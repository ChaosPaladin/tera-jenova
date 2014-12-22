package com.angelis.tera.game.domain.entity.xml.quests;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javolution.util.FastList;

import com.angelis.tera.common.domain.entity.xml.AbstractXMLEntity;
import com.angelis.tera.game.process.model.quest.enums.QuestIconTypeEnum;
import com.angelis.tera.game.process.model.quest.enums.QuestRewardTypeEnum;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "quest", namespace = "http://angelis.com/quests")
public class QuestEntity extends AbstractXMLEntity {

    private static final long serialVersionUID = -3186829934050437871L;

    @XmlAttribute(name = "quest_id")
    private int questId;

    @XmlAttribute(name = "quest_type")
    private QuestIconTypeEnum questType;

    @XmlAttribute(name = "start_npc_full_id")
    private int startNpcFullId;

    @XmlAttribute(name = "required_level")
    private int requiredLevel;

    @XmlAttribute(name = "experience_reward")
    private int experienceReward;
    
    @XmlAttribute(name = "money_reward")
    private int moneyReward;
    
    @XmlAttribute(name = "reward_type")
    private QuestRewardTypeEnum questRewardType;
    
    @XmlAttribute(name = "policy_points_reward")
    private int policyPointsReward;
    
    @XmlAttribute(name = "alliance_contribution_points_reward")
    private int allianceContributionPointsReward;
    
    @XmlAttribute(name = "reputation_points_reward")
    private int reputationPointsReward;
    
    @XmlAttribute(name = "credit_points_reward")
    private int creditPointsReward;
    
    @XmlElement(name = "required_quest", required = false, namespace = "http://angelis.com/quests")
    private List<QuestRequiredEntity> requiredQuests;
    
    @XmlElement(name = "step", namespace = "http://angelis.com/quests")
    private List<QuestStepEntity> questSteps;
    
    @XmlElement(name = "reward", namespace = "http://angelis.com/quests")
    private List<QuestRewardEntity> questRewards;

    public int getQuestId() {
        return questId;
    }

    public QuestIconTypeEnum getQuestType() {
        return questType;
    }

    public int getStartNpcFullId() {
        return startNpcFullId;
    }

    public int getRequiredLevel() {
        return requiredLevel;
    }

    public int getExperienceReward() {
        return experienceReward;
    }

    public int getMoneyReward() {
        return moneyReward;
    }

    public QuestRewardTypeEnum getQuestRewardType() {
        if (questRewardType == null) {
            questRewardType = QuestRewardTypeEnum.ALL;
        }
        return questRewardType;
    }

    public int getPolicyPointsReward() {
        return policyPointsReward;
    }

    public int getAllianceContributionPointsReward() {
        return allianceContributionPointsReward;
    }

    public int getReputationPointsReward() {
        return reputationPointsReward;
    }

    public int getCreditPointsReward() {
        return creditPointsReward;
    }

    public List<QuestRequiredEntity> getRequiredQuests() {
        return requiredQuests;
    }

    public List<QuestStepEntity> getQuestSteps() {
        if (questSteps == null) {
            questSteps = new FastList<>(0);
        }
        return questSteps;
    }

    public List<QuestRewardEntity> getQuestRewards() {
        return questRewards;
    }

    @Override
    public void onLoad() {
    }

}
