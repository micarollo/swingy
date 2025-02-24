package com.swingy.model;

import java.util.Random;

//TODO: Character?, CharacterGenerator @setter
public abstract class Hero extends Character {
    // private String p_name;
    // private String p_heroClass;
    // private int p_level;
    private int experience;
    // private int attack;
    // private int defense;
    // private int hitPoints;

    //artifacts
    // private Weapon p_weapon;
    // private Armor p_armor;
    // private Helm p_helm;

    //antiguo constructor
    // public Hero(String name, String heroClass) {
    //     this.p_name = name;
    //     this.p_heroClass = heroClass;
    //     this.p_level = 1;
    //     this.experience = 0;
    //     this.attack = new Random().nextInt(10) + 5;
    //     this.defense = new Random().nextInt(10) + 5;
    //     this.hitPoints = new Random().nextInt(20) + 30;
    // }

    public Hero(String name, int level, int attack, int defense, int hitPoints) {
        super(name, level, attack, defense, hitPoints);
        this.experience = 0;
    }

    public void gainExperience(int xp) {
        experience += xp;
        System.out.println(name + " ganó " + xp + " XP. Total: " + experience);
        levelUp();
    }

    public void levelUp() {
        int requiredXP = (level * 1000) + ((level - 1) * (level - 1) * 450);

        if (experience >= requiredXP) {
            level++;
            experience -= requiredXP;
            attack += new Random().nextInt(5) + 2;
            defense += new Random().nextInt(5) + 2;
            hitPoints += new Random().nextInt(10) + 5;
        }
    }

    public void displayStats() {
        System.out.println(getHeroClass() + " Stats for " + name + ":");
        System.out.println("Health Points (HP): " + hitPoints);
        System.out.println("Attack: " + attack);
        System.out.println("Defense: " + defense);
    }

    public abstract String getHeroClass();

}

