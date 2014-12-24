package com.angelis.tera.game.process.model.campfire;

import java.util.List;

import javolution.util.FastList;

import com.angelis.tera.game.process.controllers.CampFireController;
import com.angelis.tera.game.process.model.campfire.enums.CampFireStatusEnum;
import com.angelis.tera.game.process.model.campfire.enums.CampFireTypeEnum;
import com.angelis.tera.game.process.model.visible.VisibleTeraObject;

public class CampFire extends VisibleTeraObject {

    private CampFireTypeEnum campFireType;
    private CampFireStatusEnum campFireStatus;
    private final List<Integer> charms = new FastList<>();
    private int rate;

    public CampFire() {
        super(0, new CampFireController(), null);
        this.getController().setOwner(this);
    }

    public CampFireTypeEnum getCampFireType() {
        return campFireType;
    }

    public void setCampFireType(final CampFireTypeEnum campFireType) {
        this.campFireType = campFireType;
    }

    public CampFireStatusEnum getCampFireStatus() {
        return campFireStatus;
    }

    public void setCampFireStatus(final CampFireStatusEnum campFireStatus) {
        this.campFireStatus = campFireStatus;
    }

    public List<Integer> getCharms() {
        return charms;
    }
    
    public void addCharm(final Integer charm) {
        this.charms.add(charm);
    }
    
    public int getRate() {
        return rate;
    }

    public void setRate(final int rate) {
        this.rate = rate;
    }

    @Override
    public CampFireController getController() {
        return (CampFireController) this.controller;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof CampFire)) {
            return false;
        }
        return true;
    }
}
