package com.swingy.model;

import java.util.Random;

public abstract class Hero extends Character {
    private int experience;
    private final int startingHP;
    private int x;
    private int y;
    private Helm helm;
    private Armor armor;
    private Weapon weapon;

    public Hero(String name, int level, int attack, int defense, int hitPoints, int experience, int x, int y) {
        super(name, level, attack, defense, hitPoints);
        this.experience = experience;
        this.startingHP = hitPoints;
        this.x = x;
        this.y = y;
    }

    public boolean gainExperience(int xp) {
        experience += xp;
        return levelUp();
    }

    public boolean levelUp() {
        int requiredXP = (level * 1000) + ((level - 1) * (level - 1) * 450);

        if (experience >= requiredXP) {
            level++;
            experience -= requiredXP;
            attack += new Random().nextInt(5) + 2;
            defense += new Random().nextInt(5) + 2;
            hitPoints = getStartingHP();
            hitPoints += new Random().nextInt(10) + 5;
            return true;
        }
        return false;
    }

    public void setX(int value) {
        x = value;
    }

    public void setY(int value) {
        y = value;
    }

    public void equipArtifact(Artifact artifact) {
        if (artifact instanceof Weapon newWeapon) {
            this.weapon = newWeapon;
            attack = getAttack() + weapon.getBoost();
        } else if (artifact instanceof Armor newArmor) {
            this.armor = newArmor;
            defense = getDefense() + armor.getBoost();
        } else if (artifact instanceof Helm newHelm) {
            this.helm = newHelm;
            hitPoints = getHitPoints() + helm.getBoost();
        }
    }

    public int getExperience() { return experience; }
    public int getStartingHP() { return startingHP; }
    public int getX() { return x; }
    public int getY() { return y; }
    public abstract String getHeroClass();
}

