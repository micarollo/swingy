package com.swingy.model;

public class Warrior extends Hero {
    public Warrior(String name) {
        super(name, 1, 15, 10, 50, 0);
    }

    public Warrior(String name, int level, int attack, int defense, int hitPoints, int experience) {
        super(name, level, attack, defense, hitPoints, experience);
    }

    @Override
    public String getHeroClass() {
        return "Warrior";
    }
}