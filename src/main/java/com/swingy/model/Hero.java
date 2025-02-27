package com.swingy.model;

import java.util.Random;

public abstract class Hero extends Character {
    private int experience;

    public Hero(String name, int level, int attack, int defense, int hitPoints) {
        super(name, level, attack, defense, hitPoints);
        this.experience = 0;
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
            hitPoints += new Random().nextInt(10) + 5;
            return true;
        }
        return false;
    }

    public int getExperience() { return experience; }
    public abstract String getHeroClass();
}

