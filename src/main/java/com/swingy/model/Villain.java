package com.swingy.model;

public abstract class Villain extends Character {
    private String villainClass;

    public Villain(String name, int level, int attack, int defense, int hitPoints) {
        super(name, level, attack, defense, hitPoints);
    }

    public String getVillainClass() {
        return villainClass;
    }
}
