package com.swingy.controller;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import com.swingy.DbManager;
import com.swingy.model.Hero;
import com.swingy.model.Mage;
import com.swingy.model.Warrior;
import com.swingy.view.ConsoleView;

public class HeroController {
    private final ConsoleView consoleView;
    private final MapController mapController;
    // private final VillainController villainController;
    private final GameController gameController;
    private final DbManager dbManager;
    private Hero hero;
    // private int x;
    // private int y;
    Random random = new Random();

    public HeroController(ConsoleView consoleView, MapController mapController, GameController gameController, DbManager dbManager) {
        this.consoleView = consoleView;
        this.mapController = mapController;
        this.gameController = gameController;
        this.dbManager = dbManager;
        // this.villainController = villainController;
        // this.x = posX;
        // this.y = posY;
        // this.x = mapController.getSize() / 2;
        // this.y = mapController.getSize() / 2;
    }

    public Hero HeroCreator() {
        int choice = consoleView.chooseHeroClass();
        String name = consoleView.chooseHeroName();
        // Hero hero;

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
        hero.setX(mapController.getSize() / 2);
        hero.setY(mapController.getSize() / 2);
        // mapController.setCell(hero.getX(), hero.getY(), 2);
        return hero;
    }

    public void moveHero(int nx, int ny) {
        int result = mapController.isValidMove((hero.getX() + nx), (hero.getY() + ny));
        // Random random = new Random();
        switch (result) {
            case 0:
                mapController.updateMap(hero.getX(), hero.getY(), 0);
                updateHeroPosition((hero.getX() + nx), (hero.getY() + ny));
                mapController.updateMap(hero.getX(), hero.getY(), 2);
                dbManager.updateHeroPos(hero.getX(), hero.getY(), hero.getName());
                break;
            case 1:
                int choose = consoleView.displayFightorRun();
                if (choose == 1)
                {
                    gameController.handleBattle(hero, (hero.getX() + nx), (hero.getY() + ny));
                } else {
                    // int luck = random.nextInt(2);
                    int luck = ThreadLocalRandom.current().nextInt(2);
                    System.out.println("luck: " + luck);
                    if (luck == 0) {
                        runAway();
                    } else {
                        System.out.println("Bad luck, the villain don't let you run and you have to fight!!");
                        gameController.handleBattle(hero, (hero.getX() + nx), (hero.getY() + ny));
                    }
                }
            case -1:
                //displayOutOfMap();
                break;
        }
    }
		
    public void runAway() {
        // Random random = new Random();
        int size = mapController.getSize();
        int newX, newY;
        do { 
            newX = random.nextInt(size);
            newY = random.nextInt(size);
        } while (mapController.getCell(newX, newY) != 0);
        mapController.setCell(hero.getX(), hero.getY(), 0);
        mapController.setCell(newX, newY, 2);
        updateHeroPosition(newX, newY);
        dbManager.updateHeroPos(newX, newY, hero.getName());
        
        // x = newX;
        // y = newY;
        consoleView.runMsg();	
    }

    public void updateHeroPosition(int newX, int newY) {
        hero.setX(newX);
        hero.setY(newY);
        // x = newX;
        // y = newY;
    }

    // public int getX() {
    //     return x;
    // }

    // public int getY() {
    //     return y;
    // }

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }
}