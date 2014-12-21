package com.angelis.tera.game.process.model.action;

import com.angelis.tera.common.utils.Point3D;
import com.angelis.tera.game.process.model.AbstractUniqueTeraModel;
import com.angelis.tera.game.process.model.attack.enums.AttackTypeEnum;
import com.angelis.tera.game.process.model.creature.AbstractCreature;
import com.angelis.tera.game.process.model.visible.WorldPosition;

public class Action extends AbstractUniqueTeraModel {

    private AbstractCreature target;
    private int skillId;
    private AttackTypeEnum attackType;
    private WorldPosition startPosition;
    private Point3D endPosition;
    private int stage;

    public Action() {
        super(0);
    }

    public AbstractCreature getTarget() {
        return target;
    }

    public void setTarget(final AbstractCreature target) {
        this.target = target;
    }

    public int getSkillId() {
        return skillId;
    }

    public void setSkillId(final int skillId) {
        this.skillId = skillId;
    }

    public AttackTypeEnum getAttackType() {
        return attackType;
    }

    public void setAttackType(final AttackTypeEnum attackType) {
        this.attackType = attackType;
    }

    public WorldPosition getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(final WorldPosition startPosition) {
        this.startPosition = startPosition;
    }

    public Point3D getEndPosition() {
        return endPosition;
    }

    public void setEndPosition(final Point3D endPosition) {
        this.endPosition = endPosition;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(final int stage) {
        this.stage = stage;
    }

    public Object getVisualEffect() {
        // TODO Auto-generated method stub
        return null;
    }
}
