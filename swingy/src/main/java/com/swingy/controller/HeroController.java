package com.swingy.controller;

import com.swingy.model.Hero;
import com.swingy.model.Mage;
import com.swingy.model.Warrior;
import com.swingy.view.HeroView;

public class HeroController {
    private final HeroView view;

    public HeroController(HeroView view) {
        this.view = view;
    }

    public Hero HeroCreator() {
        int choice = view.setHeroClass();
        String name = view.setHeroName();
        Hero hero;

        switch (choice) {
            case 1:
                hero = new Warrior(name);
                view.displayHeroCreation("Warrior", name);
                break;
            case 2:
                hero = new Mage(name);
                view.displayHeroCreation("Mage", name);
                break;
            default:
                hero = new Warrior("Default warrior");
                view.displayHeroCreation("Warrior", name);
                break;
        }
        return hero;
    }
}