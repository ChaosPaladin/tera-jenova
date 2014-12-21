package com.angelis.tera.game.xml.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.angelis.tera.common.domain.entity.xml.AbstractXMLEntity;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "basestats", namespace = "http://angelis.com/base")
public class BaseStatsEntity extends AbstractXMLEntity {

    private static final long serialVersionUID = -7818567145741615843L;

    @XmlAttribute(name = "level")
    private int level;

    @XmlAttribute(name = "base_hp")
    private int baseHp;

    @XmlAttribute(name = "base_mp")
    private int baseMp;

    @XmlAttribute(name = "attack")
    private int attack;

    @XmlAttribute(name = "defense")
    private int defense;

    @XmlAttribute(name = "impact")
    private int impact;

    @XmlAttribute(name = "balance")
    private int balance;

    @XmlAttribute(name = "crit_rate")
    private int critRate;

    @XmlAttribute(name = "crit_resistance")
    private int critResistance;

    @XmlAttribute(name = "crit_power")
    private int critPower;

    @XmlAttribute(name = "power")
    private int power;

    @XmlAttribute(name = "endurance")
    private int endurance;

    @XmlAttribute(name = "impact_factor")
    private int impactFactor;

    @XmlAttribute(name = "balance_factor")
    private int balanceFactor;

    @XmlAttribute(name = "attack_speed")
    private int attackSpeed;

    @XmlAttribute(name = "speed")
    private int speed;

    @XmlAttribute(name = "weakening_resistance")
    private int weakeningResistance;

    @XmlAttribute(name = "periodic_resistance")
    private int periodicResistance;

    @XmlAttribute(name = "stun_resistance")
    private int stunResistance;

    @XmlAttribute(name = "natural_mp_regen")
    private int naturalMpRegen;

    @XmlAttribute(name = "natural_mp_degen")
    private int naturalMpDegen;

    @XmlAttribute(name = "combat_hp_regen")
    private int combatHpRegen;

    @XmlAttribute(name = "combat_mp_regen")
    private int combatMpRegen;

    public int getLevel() {
        return level;
    }

    public int getBaseHp() {
        return baseHp;
    }

    public int getBaseMp() {
        return baseMp;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public int getImpact() {
        return impact;
    }

    public int getBalance() {
        return balance;
    }

    public int getCritRate() {
        return critRate;
    }

    public int getCritResistance() {
        return critResistance;
    }

    public int getCritPower() {
        return critPower;
    }

    public int getPower() {
        return power;
    }

    public int getEndurance() {
        return endurance;
    }

    public int getImpactFactor() {
        return impactFactor;
    }

    public int getBalanceFactor() {
        return balanceFactor;
    }

    public int getAttackSpeed() {
        return attackSpeed;
    }

    public int getSpeed() {
        return speed;
    }

    public int getWeakeningResistance() {
        return weakeningResistance;
    }

    public int getPeriodicResistance() {
        return periodicResistance;
    }

    public int getStunResistance() {
        return stunResistance;
    }

    public int getNaturalMpRegen() {
        return naturalMpRegen;
    }

    public int getNaturalMpDegen() {
        return naturalMpDegen;
    }

    public int getCombatHpRegen() {
        return combatHpRegen;
    }

    public int getCombatMpRegen() {
        return combatMpRegen;
    }
}
