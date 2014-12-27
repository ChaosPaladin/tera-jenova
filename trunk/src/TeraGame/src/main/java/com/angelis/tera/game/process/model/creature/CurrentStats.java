package com.angelis.tera.game.process.model.creature;

public class CurrentStats {

    private int hp;
    private int mp;
    private int re;
    private int stamina;
    private int speed;
    
    public int getHp() {
        return hp;
    }

    public void setHp(final int hp) {
        this.hp = hp;
        if (this.hp < 0) {
            this.hp = 0;
        }
    }
    
    public void addHp(final int hp) {
        this.setHp(this.hp + hp);
    }
    
    public void removeHp(final int hp) {
        this.setHp(this.hp - hp);
    }

    public int getMp() {
        return mp;
    }

    public void setMp(final int mp) {
        this.mp = mp;
        if (this.mp < 0) {
            this.mp = 0;
        }
    }

    public void addMp(final int mp) {
        this.setMp(this.mp + mp);
    }
    
    public void removeMp(final int mp) {
        this.setMp(this.mp - mp);
    }

    public int getRe() {
        return re;
    }

    public void setRe(final int re) {
        this.re = re;
        if (this.re > 100) {
            this.re = 100;
        }
        if (this.re < 0) {
            this.re = 0;
        }
    }

    public void addRe(final int re) {
        this.setRe(this.re + re);
    }

    public int getStamina() {
        return stamina;
    }

    public void setStamina(final int stamina) {
        this.stamina = stamina;
        if (this.stamina > 120) {
            this.stamina = 120;
        }
        if (this.stamina < 0) {
            this.stamina = 0;
        }
    }

    public void addStamina(final int stamina) {
        this.setStamina(this.stamina + stamina);
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(final int speed) {
        this.speed = speed;
    }

    public boolean isDead() {
        return this.hp <= 0;
    }
}
