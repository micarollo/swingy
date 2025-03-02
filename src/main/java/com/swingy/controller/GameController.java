package com.swingy.controller;

import java.util.Random;

import com.swingy.model.Hero;
import com.swingy.model.Villain;
import com.swingy.view.ConsoleView;

public class GameController {
    private final MapController mapController;
    private final ConsoleView consoleView;
    private final HeroController heroController;
    private final VillainController villainController;
    private Hero hero;

    public GameController() {
        this.consoleView = new ConsoleView();
        this.mapController = new MapController();
        mapController.createMap(1);
        this.villainController = new VillainController();
        // this.heroController = new HeroController(consoleView, mapController, villainController);
        this.heroController = new HeroController(consoleView, mapController, this);
    }

    public void startGame() {
        // int size = mapController.getSize();
        hero = heroController.HeroCreator();
        consoleView.displayHeroStats(hero);
        consoleView.displayMap(mapController.getMap());
        gameLoop();
    }

    public void gameLoop() {
        while (true) { 
            int input = consoleView.displayMenu();
            handleInput(input);
            consoleView.displayMap(mapController.getMap());
        }
    }

    public void handleInput(int input) {
        switch (input) {
            case 1:
                heroController.moveHero(-1, 0);
                break;
            case 2:
                heroController.moveHero(1, 0);
                break;
            case 3:
                heroController.moveHero(0, 1);
                break;
            case 4:
                heroController.moveHero(0, -1);
                break;
            case 5:
                consoleView.displayHeroStats(hero);
                break;
            case 6:
                consoleView.displayExitMessage();
                exitGame();
                break;
            default:
                throw new AssertionError();
        }
    }

    public void handleBattle(Hero hero, int newX, int newY) {
        Random random = new Random();
        Villain villain = villainController.villainCreator(hero.getLevel());
        double heroDodge = 0.2;
        double villainDodge = 0.1;
        System.out.println("<<-------------FIGHTING------------>>");
        consoleView.displayHeroBattleStats(hero);
        consoleView.displayVillainStats(villain);
        while (hero.isAlive() && villain.isAlive()) {
            if (random.nextDouble() >= villainDodge) {
                int heroDamage = hero.getAttack() + random.nextInt(5);
                villain.takeDamage(heroDamage);
                System.out.println("VILLAIN HP: " + villain.getHitPoints());
            } else 
                System.out.println("Villain dodged the attack!");
            if (!villain.isAlive())
            {
                System.out.println("You win");
                System.out.println("<<--------------END------------->");
                mapController.setCell(heroController.getX(), heroController.getY(), 0);
                mapController.setCell(newX, newY, 2);
                heroController.updateHeroPosition(newX, newY);
                gainHeroExperience(hero, villain);
                return;
            }
            if (random.nextDouble() >= heroDodge) {
                int villainDamage = villain.getAttack() + random.nextInt(3);
                hero.takeDamage(villainDamage);
                System.out.println("HERO HP: " + hero.getHitPoints());
            } else 
                System.out.println("Hero dodged the attack!");
            if (!hero.isAlive())
            {
                consoleView.gameOver();
            }
        }   
	}

    public void gainHeroExperience(Hero hero, Villain villain) {
        int power = villain.getPower();
        int xpGained = power * 10;
        boolean levelUp = hero.gainExperience(xpGained);
        // System.out.println("You gained " + xpGained + " experience.");
        // System.out.println("Total Experience: " + hero.getExperience());
        // System.out.println("Current Level: " + hero.getLevel());
        consoleView.displayExperienceGain(hero, xpGained);
        if (levelUp) {
            mapController.changeLevel(hero.getLevel());
            heroController.updateHeroPosition((mapController.getSize() / 2), (mapController.getSize() / 2));
            System.out.println("X: " + heroController.getX() + " Y: " + heroController.getY());
            System.out.println("HP: " + hero.getHitPoints());
        }
    }

    public void exitGame() {
        System.exit(0);
    }
}