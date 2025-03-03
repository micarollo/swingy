package com.swingy.model;

import java.util.Random;

public abstract class Hero extends Character {
    private int experience;
    private final int startingHP;
    private int x;
    private int y;

    public Hero(String name, int level, int attack, int defense, int hitPoints, int experience) {
        super(name, level, attack, defense, hitPoints);
        this.experience = experience;
        this.startingHP = hitPoints;
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

    public int getExperience() { return experience; }
    public int getStartingHP() { return startingHP; }
    public int getX() { return x; }
    public int getY() { return y; }
    public abstract String getHeroClass();
}

