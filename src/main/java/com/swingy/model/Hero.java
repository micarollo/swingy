package main.java.com.swingy.model;

import java.util.Random;

public class Hero {
    private String p_name;
    private String p_heroClass;
    private int p_level;
    private int p_experiencie;
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
        this.p_experiencie = 0;
        this.p_attack = new Random().nextInt(10) + 5;
        this.p_defense = new Random().nextInt(10) + 5;
        this.p_hitPoints = new Random().nextInt(20) + 30;
    }
}
