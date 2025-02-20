package com.swingy;

import java.util.Random;

//TODO: Character?, CharacterGenerator @setter
public class Hero {
    private String p_name;
    private String p_heroClass;
    private int p_level;
    private int p_experience;
    private int p_attack;
    private int p_defense;
    private int p_hitPoints;

    private Weapon p_weapon;
    private Armor p_armor;
    private Helm p_helm;

    public Hero(String name, String heroClass) {
        this.p_name = name;
        this.p_heroClass = heroClass;
        this.p_level = 1;
        this.p_experience = 0;
        this.p_attack = new Random().nextInt(10) + 5;
        this.p_defense = new Random().nextInt(10) + 5;
        this.p_hitPoints = new Random().nextInt(20) + 30;
    }

    public void levelUp() {
        int requiredXP = (p_level * 1000) + ((p_level - 1) * (p_level - 1) * 450);

        if (p_experience >= requiredXP) {
            p_level++;
            p_experience -= requiredXP;
            p_attack += new Random().nextInt(5) + 2;
            p_defense += new Random().nextInt(5) + 2;
            p_hitPoints += new Random().nextInt(10) + 5;

            // System.out.println("Nuevo ataque: " + p_attack + ", Defensa: " + p_defense + ", HP: " + p_hitPoints);
        }
    }

}

