package com.swingy.controller;

import com.swingy.model.Hero;
import com.swingy.model.Mage;
import com.swingy.model.Warrior;
import com.swingy.view.ConsoleView;

public class HeroController {
    private final ConsoleView consoleView;
    private int x;
    private int y;

    public HeroController(ConsoleView consoleView) {
        this.consoleView = consoleView;
    }

    public Hero HeroCreator() {
        int choice = consoleView.chooseHeroClass();
        String name = consoleView.chooseHeroName();
        Hero hero;

        switch (choice) {
            case 1:
                hero = new Warrior(name);
                consoleView.displayHeroCreation("Warrior", name);
                break;
            case 2:
                hero = new Mage(name);
                consoleView.displayHeroCreation("Mage", name);
                break;
            default:
                hero = new Warrior("Default warrior");
                consoleView.displayHeroCreation("Warrior", name);
                break;
        }
        return hero;
    }

    public void moveHero(int direction) {
        switch (direction) {
            case 1:
                y++;
                break;
            case 2:
                y--;
                break;
            case 3:
                x++;
                break;
            case 4:
                x--;
                break;
        }
    }
}