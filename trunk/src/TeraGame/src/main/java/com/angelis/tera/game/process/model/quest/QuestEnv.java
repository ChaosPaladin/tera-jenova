package com.angelis.tera.game.process.model.quest;

import java.util.Map;

import javolution.util.FastMap;

public class QuestEnv {

    private final Quest quest;

    private int currentStep = 0;
    private Map<Integer, Integer> counters;
    private boolean complited;

    public QuestEnv(final Quest quest) {
        this.quest = quest;
    }

    public Quest getQuest() {
        return quest;
    }
    
    public int getCurrentStep() {
        return currentStep;
    }
    
    public void setCurrentStep(final int currentStep) {
        this.currentStep = currentStep;
    }
    
    public QuestStep getCurrentQuestStep() {
        return this.quest.getQuestSteps().get(this.currentStep);
    }

    public void addStep() {
        this.currentStep++;
        this.counters.clear();
    }
    
    public Map<Integer, Integer> getCounters() {
        if (counters == null) {
            counters = new FastMap<>(0);
        }
        return counters;
    }
    
    public void addCounter(final int fullId) {
        Integer oldValue = this.getCounters().get(fullId);
        if (oldValue == null) {
            oldValue = 0;
            this.counters.put(fullId, oldValue);
        }
        
        oldValue++;
    }
    
    public void setCounter(final int objectId, final int amount) {
        this.counters.put(objectId, amount);
    }

    public void setCounters(final Map<Integer, Integer> counters) {
        this.counters = counters;
    }

    public boolean isComplited() {
        return complited;
    }

    public void setComplited(final boolean complited) {
        this.complited = complited;
    }

    public final boolean isCurrentQuestStepLast() {
        return this.currentStep == (this.quest.getQuestSteps().size()-1);
    }
    
    public boolean hasAllCounters() {
        boolean hasAllCounters = true;
        for (final QuestStepValue questStepValue : this.getCurrentQuestStep().getQuestStepValues()) {
            final Integer questCounters = this.getCounters().get(questStepValue.getObjectId());
            if (questCounters == null || questCounters < questStepValue.getAmount()) {
                hasAllCounters = false;
                break;
            }
        }
        return hasAllCounters;
    }

    public int getNpcSize() {
        // TODO
        return 0;
    }
}
