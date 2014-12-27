package com.angelis.tera.game.process.model.quest;

public class QuestStepValue {

    private final int objectId;
    private final int amount;
    
    public QuestStepValue(final int objectId, final int amount) {
        this.objectId = objectId;
        this.amount = amount;
    }

    public int getObjectId() {
        return objectId;
    }

    public int getAmount() {
        return amount;
    }
}
