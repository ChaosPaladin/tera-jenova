package com.angelis.tera.game.process.model.creature;

import com.angelis.tera.common.process.model.AbstractModel;


public final class BaseStats extends AbstractModel {

    private int level;
    private int baseHp;
    private int baseMp;
    private int baseRe;
    private int attack;
    private int defense;
    private int impact;
    private int balance;
    private int critRate;
    private int critResistance;
    private int critPower;
    private int power;
    private int endurance;
    private int impactFactor;
    private int balanceFactor;
    private int attackSpeed;
    private int speed;
    private int weakeningResistance;
    private int periodicResistance;
    private int stunResistance;
    private int naturalMpRegen;
    private int naturalMpDegen;
    private int combatHpRegen;
    private int combatMpRegen;

    public BaseStats() {
        super(null);
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(final int level) {
        this.level = level;
    }

    public int getBaseHp() {
        return baseHp;
    }

    public void setBaseHp(final int baseHp) {
        this.baseHp = baseHp;
    }

    public int getBaseMp() {
        return baseMp;
    }

    public void setBaseMp(final int baseMp) {
        this.baseMp = baseMp;
    }

    public int getBaseRe() {
        return baseRe;
    }

    public void setBaseRe(final int baseRe) {
        this.baseRe = baseRe;
    }

    public int getAttack() {
        return attack;
    }
    
    public int getMinAttack() {
        // TODO
        return this.attack;
    }
    
    public int getMaxAttack() {
        // TODO
        return this.attack;
    }

    public void setAttack(final int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(final int defense) {
        this.defense = defense;
    }

    public int getImpact() {
        return impact;
    }

    public void setImpact(final int impact) {
        this.impact = impact;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(final int balance) {
        this.balance = balance;
    }

    public int getCritRate() {
        return critRate;
    }

    public void setCritRate(final int critRate) {
        this.critRate = critRate;
    }

    public int getCritResistance() {
        return critResistance;
    }

    public void setCritResistance(final int critResistance) {
        this.critResistance = critResistance;
    }

    public int getCritPower() {
        return critPower;
    }

    public void setCritPower(final int critPower) {
        this.critPower = critPower;
    }

    public int getPower() {
        return power;
    }

    public void setPower(final int power) {
        this.power = power;
    }

    public int getEndurance() {
        return endurance;
    }

    public void setEndurance(final int endurance) {
        this.endurance = endurance;
    }

    public int getImpactFactor() {
        return impactFactor;
    }

    public void setImpactFactor(final int impactFactor) {
        this.impactFactor = impactFactor;
    }

    public int getBalanceFactor() {
        return balanceFactor;
    }

    public void setBalanceFactor(final int balanceFactor) {
        this.balanceFactor = balanceFactor;
    }

    public int getAttackSpeed() {
        return attackSpeed;
    }

    public void setAttackSpeed(final int attackSpeed) {
        this.attackSpeed = attackSpeed;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(final int speed) {
        this.speed = speed;
    }

    public int getWeakeningResistance() {
        return weakeningResistance;
    }

    public void setWeakeningResistance(final int weakeningResistance) {
        this.weakeningResistance = weakeningResistance;
    }

    public int getPeriodicResistance() {
        return periodicResistance;
    }

    public void setPeriodicResistance(final int periodicResistance) {
        this.periodicResistance = periodicResistance;
    }

    public int getStunResistance() {
        return stunResistance;
    }

    public void setStunResistance(final int stunResistance) {
        this.stunResistance = stunResistance;
    }

    public int getNaturalMpRegen() {
        return naturalMpRegen;
    }

    public void setNaturalMpRegen(final int naturalMpRegen) {
        this.naturalMpRegen = naturalMpRegen;
    }

    public int getNaturalMpDegen() {
        return naturalMpDegen;
    }

    public void setNaturalMpDegen(final int naturalMpDegen) {
        this.naturalMpDegen = naturalMpDegen;
    }

    public int getCombatHpRegen() {
        return combatHpRegen;
    }

    public void setCombatHpRegen(final int combatHpRegen) {
        this.combatHpRegen = combatHpRegen;
    }

    public int getCombatMpRegen() {
        return combatMpRegen;
    }

    public void setCombatMpRegen(final int combatMpRegen) {
        this.combatMpRegen = combatMpRegen;
    }

    public double getHpStamina() {
        return 0;
    }

    public double getMpStamina() {
        return 0;
    }
}
