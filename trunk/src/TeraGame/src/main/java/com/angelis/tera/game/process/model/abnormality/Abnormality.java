package com.angelis.tera.game.process.model.abnormality;

import com.angelis.tera.game.process.model.AbstractTeraModel;
import com.angelis.tera.game.process.model.creature.AbstractCreature;

public class Abnormality extends AbstractTeraModel {

    private AbstractCreature caster;
    private AbstractCreature target;
    private int timeLeft;

    private int delay;

    private int hpModifier;
    private int mpModifier;
    private int speedModifier;

    public Abnormality(final Integer id) {
        super(id);
    }

    public AbstractCreature getCaster() {
        return caster;
    }

    public void setCaster(final AbstractCreature caster) {
        this.caster = caster;
    }

    public AbstractCreature getTarget() {
        return target;
    }

    public void setTarget(final AbstractCreature target) {
        this.target = target;
    }

    public int getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(final int timeLeft) {
        this.timeLeft = timeLeft;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(final int delay) {
        this.delay = delay;
    }

    public int getHpModifier() {
        return hpModifier;
    }

    public void setHpModifier(final int hpModifier) {
        this.hpModifier = hpModifier;
    }

    public int getMpModifier() {
        return mpModifier;
    }

    public void setMpModifier(final int mpModifier) {
        this.mpModifier = mpModifier;
    }

    public int getSpeedModifier() {
        return speedModifier;
    }

    public void setSpeedModifier(final int speedModifier) {
        this.speedModifier = speedModifier;
    }
}
