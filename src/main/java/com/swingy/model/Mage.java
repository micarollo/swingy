package com.swingy.model;

public class Mage extends Hero {
    public Mage(String name) {
        super(name, 1, 10, 5, 40);
    }

    @Override
    public String getHeroClass() {
        return "Mage";
    }
}