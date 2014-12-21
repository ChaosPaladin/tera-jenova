package com.angelis.tera.game.domain.entity.database;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.angelis.tera.common.domain.entity.database.AbstractDatabaseEntity;

@Entity
@Table(name = "player_quests")
public class QuestEntity extends AbstractDatabaseEntity {

    private static final long serialVersionUID = -5365265045972985699L;

    @Column(name = "questId")
    private int questId;

    @Column(name = "current_step")
    private int currentStep;
    
    @Column(name = "complited")
    private boolean complited;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "player_id", nullable = false)
    private PlayerEntity player;

    public QuestEntity(final Integer id) {
        super(id);
    }

    public QuestEntity() {
    }

    public int getQuestId() {
        return questId;
    }

    public void setQuestId(final int questId) {
        this.questId = questId;
    }

    public int getCurrentStep() {
        return currentStep;
    }

    public void setCurrentStep(final int currentStep) {
        this.currentStep = currentStep;
    }

    public boolean isComplited() {
        return complited;
    }

    public void setComplited(final boolean complited) {
        this.complited = complited;
    }

    public PlayerEntity getPlayer() {
        return player;
    }

    public void setPlayer(final PlayerEntity player) {
        this.player = player;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = prime * ((player == null) ? 0 : player.hashCode());
        result = prime * result + questId;
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof QuestEntity)) {
            return false;
        }
        final QuestEntity other = (QuestEntity) obj;
        if (player == null) {
            if (other.player != null) {
                return false;
            }
        }
        else if (!player.equals(other.player)) {
            return false;
        }
        if (questId != other.questId) {
            return false;
        }
        return true;
    }
}
