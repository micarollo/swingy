package com.swingy.controller;

import java.util.Random;
import java.util.Scanner;

import com.swingy.DbManager;
import com.swingy.model.Artifact;
import com.swingy.model.ArtifactGenerator;
import com.swingy.model.Hero;
import com.swingy.model.Villain;
import com.swingy.view.ConsoleView;

public class GameController {
    private final DbManager dbManager;
    private final MapController mapController;
    private final ConsoleView consoleView;
    private final HeroController heroController;
    private final VillainController villainController;
    private Hero hero;

    public GameController() {
        this.dbManager = new DbManager();
        this.consoleView = new ConsoleView();
        this.mapController = new MapController();
        mapController.createMap(1);
        this.villainController = new VillainController();
        // this.heroController = new HeroController(consoleView, mapController, villainController);
        this.heroController = new HeroController(consoleView, mapController, this, dbManager);
    }

    public void startGame() {
        Scanner scan = new Scanner(System.in);
        // int size = mapController.getSize();
        int choice = consoleView.displayWelcomeMessage();
        if (choice == 1) {
            dbManager.displayHeroes();
            int id = scan.nextInt();
            hero = dbManager.getHeroById(id);
            heroController.setHero(hero);
            System.out.println("x: " + hero.getX());
            mapController.createMapfromDb(hero.getLevel(), hero.getX(), hero.getY());
            // mapController.setCell(mapController.getSize() / 2, mapController.getSize() / 2, 0);
            // mapController.setCell(hero.getX(), hero.getY(), 2);
            System.out.println(hero);
        }
        else {
            hero = heroController.HeroCreator();
            dbManager.saveHero(hero);
        }
        consoleView.displayHeroStats(hero);
        consoleView.displayMap(mapController.getMap());
        gameLoop();
    }

    public void gameLoop() {
        while (true) { 
            int input = consoleView.displayMenu();
            if (input != -1) {
                handleInput(input);
                consoleView.displayMap(mapController.getMap());
            }
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
        consoleView.villainAppears(villain);
        System.out.println("<<-------------FIGHTING------------>>");
        // consoleView.displayHeroBattleStats(hero);
        // consoleView.displayVillainStats(villain);
        while (hero.isAlive() && villain.isAlive()) {
            try {
                if (random.nextDouble() >= villainDodge) {
                    int heroDamage = hero.getAttack() + random.nextInt(5);
                    villain.takeDamage(heroDamage);
                    System.out.println("\uD83D\uDDE1 hero attacks! (Villain HP: " + villain.getHitPoints() + ")");
                    System.out.println();
                    Thread.sleep(500);
                } else {
                    System.out.println("\uD83D\uDCA8 Villain dodged the attack!");
                    System.out.println();
                    Thread.sleep(300);
                }
                if (!villain.isAlive())
                {
                    System.out.println("\uD83D\uDCAA You win");
                    System.out.println("<<--------------END------------->");
                    System.out.println();
                    handleDropArtifact(villain.getLevel());
                    mapController.setCell(hero.getX(), hero.getY(), 0);
                    mapController.setCell(newX, newY, 2);
                    heroController.updateHeroPosition(newX, newY);
                    gainHeroExperience(hero, villain);
                    dbManager.saveOrUpdateHero(hero);
                    Thread.sleep(1000);
                    return;
                }
                if (random.nextDouble() >= heroDodge) {
                    int villainDamage = villain.getAttack() + random.nextInt(3);
                    hero.takeDamage(villainDamage);
                    System.out.println("\uD83D\uDCA5 Villain attacks! (Hero HP: " + hero.getHitPoints() + ")");
                    System.out.println();
                    Thread.sleep(500);
                } else {
                    System.out.println("\uD83D\uDCA8 Hero dodged the attack!");
                    System.out.println();
                    Thread.sleep(300);
                }
                if (!hero.isAlive())
                {
                    dbManager.deleteHero(hero.getName());
                    consoleView.gameOver();
                    Thread.sleep(1000);

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }   
	}

    public void handleDropArtifact(int villainLevel) {
        ArtifactGenerator artifactGenerator = new ArtifactGenerator();
        Random random = new Random();
        if (random.nextDouble() < 0.4) {
            Artifact artifact = artifactGenerator.generateArtifact(villainLevel);
            // consoleView.displayHeroStats(hero);
            consoleView.displayActualArtifacts(hero);
            int res = consoleView.artifactMsg(artifact);
            if (res == 1) {
                hero.equipArtifact(artifact);
                consoleView.displayHeroStats(hero);
            }
        }
        else
            System.out.println("bad luck: the villain didnt drop any artifact!!");
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
            System.out.println("X: " + hero.getX() + " Y: " + hero.getY());
            System.out.println("HP: " + hero.getHitPoints());
        }
    }

    public void exitGame() {
        System.exit(0);
    }
}