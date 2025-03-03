package com.swingy.model;

public class Mage extends Hero {
    public Mage(String name) {
        super(name, 1, 10, 7, 40, 0);
    }

    public Mage(String name, int level, int attack, int defense, int hitPoints, int experience) {
        super(name, level, attack, defense, hitPoints, experience);
    }

    @Override
    public String getHeroClass() {
        return "Mage";
    }
}