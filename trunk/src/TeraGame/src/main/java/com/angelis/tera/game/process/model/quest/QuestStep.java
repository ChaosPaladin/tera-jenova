package com.angelis.tera.game.process.model.quest;

import java.util.List;

import com.angelis.tera.game.process.model.quest.enums.QuestStepTypeEnum;

public final class QuestStep {

    private final QuestStepTypeEnum questStepType;
    private final List<QuestStepValue> questStepValues;
    
    public QuestStep(final QuestStepTypeEnum questStepType, final List<QuestStepValue> stepValues) {
        this.questStepType = questStepType;
        this.questStepValues = stepValues;
    }

    public QuestStepTypeEnum getQuestStepType() {
        return questStepType;
    }

    public List<QuestStepValue> getQuestStepValues() {
        return questStepValues;
    }
    
    public final boolean containsObjectId(final int objectId) {
        for (final QuestStepValue questStepValue : questStepValues) {
            if (questStepValue.getObjectId() == objectId) {
                return true;
            }
        }
        return false;
    }
    
    public int getRequiredObjectAmountByObjectId(final int objectId) {
        int amount = 0;
        for (final QuestStepValue questStepValue : questStepValues) {
            if (questStepValue.getObjectId() == objectId) {
                amount = questStepValue.getAmount();
                break;
            }
        }
        return amount;
    }
}
