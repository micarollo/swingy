package com.swingy.model;

public class Warrior extends Hero {
    public Warrior(String name) {
        super(name, 1, 15, 10, 50);
    }

    @Override
    public String getHeroClass() {
        return "Warrior";
    }
}