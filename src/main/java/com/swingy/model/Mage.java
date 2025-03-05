package com.swingy.model;

public class Mage extends Hero {
    public Mage(String name) {
        super(name, 1, 10, 7, 40, 0, 0, 0);
    }

    public Mage(String name, int level, int attack, int defense, int hitPoints, int experience, int x, int y) {
        super(name, level, attack, defense, hitPoints, experience, x, y);
    }

    @Override
    public String getHeroClass() {
        return "Mage";
    }
}