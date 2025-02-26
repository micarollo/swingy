package com.swingy.controller;

import com.swingy.model.Hero;
import com.swingy.model.Mage;
import com.swingy.model.Warrior;
import com.swingy.view.ConsoleView;

public class HeroController {
    private final ConsoleView consoleView;
    private final MapController mapController;
    private int x;
    private int y;

    public HeroController(ConsoleView consoleView, MapController mapController) {
        this.consoleView = consoleView;
        this.mapController = mapController;
        // this.x = posX;
        // this.y = posY;
        this.x = mapController.getSize() / 2;
        this.y = mapController.getSize() / 2;
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
        mapController.setCell(x, y, 2);
        return hero;
    }

    public void moveHero(int nx, int ny) {
        // int newX = x + nx;
        // int newY = y + ny;
        // //chequear que sea valido el mov;
        // x = newX;
        // y = newY;
        int result = mapController.isValidMove((x + nx), (y + ny));
        switch (result) {
            case 0:
                mapController.updateMap(x, y, 0);
                x = x + nx;
                y = y + ny;
                mapController.updateMap(x, y, 2);
                break;
            case 1:
                // displayBattle();
                break;
            case -1:
                //displayOutOfMap();
                break;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}