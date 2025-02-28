package com.swingy.controller;

import java.util.Random;

import com.swingy.model.Hero;
import com.swingy.model.Mage;
import com.swingy.model.Warrior;
import com.swingy.view.ConsoleView;

public class HeroController {
    private final ConsoleView consoleView;
    private final MapController mapController;
    // private final VillainController villainController;
    private final GameController gameController;
    private Hero hero;
    private int x;
    private int y;

    public HeroController(ConsoleView consoleView, MapController mapController, GameController gameController) {
        this.consoleView = consoleView;
        this.mapController = mapController;
        this.gameController = gameController;
        // this.villainController = villainController;
        // this.x = posX;
        // this.y = posY;
        this.x = mapController.getSize() / 2;
        this.y = mapController.getSize() / 2;
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
        mapController.setCell(x, y, 2);
        return hero;
    }

    public void moveHero(int nx, int ny) {
        int result = mapController.isValidMove((x + nx), (y + ny));
        switch (result) {
            case 0:
                mapController.updateMap(x, y, 0);
                x = x + nx;
                y = y + ny;
                mapController.updateMap(x, y, 2);
                break;
            case 1:
                int choose = consoleView.displayFightorRun();
                if (choose == 1)
                {
                    gameController.handleBattle(hero, (x + nx), (y + ny));
                } else
                    runAway();
                // if (handleBattle(choose, (x + nx), (y + ny))) {
                //     mapController.updateMap(x, y, 0);
                //     x = x + nx;
                //     y = y + ny;
                //     mapController.updateMap(x, y, 2);
                // }
                // break;
            case -1:
                //displayOutOfMap();
                break;
        }
    }

    // public boolean handleBattle(int choose, int villainX, int villainY) {
    //     Random random = new Random();
    //     if (choose == 1) {
    //         Villain villain = villainController.villainCreator(hero.getLevel());
    //         double heroDodge = 0.2;
    //         double villainDodge = 0.1;
    //         System.out.println("<<-----FIGHTING----->>");
    //         while (hero.isAlive() && villain.isAlive()) {
    //             if (random.nextDouble() >= villainDodge) {
    //                 int heroDamage = hero.getAttack() + random.nextInt(5);
    //                 villain.takeDamage(heroDamage);
    //                 System.out.println("VILLAIN HP: " + villain.getHitPoints());
    //             } else 
    //                 System.out.println("Villain dodged the attack!");
    //             if (!villain.isAlive())
    //             {
    //                 System.out.println("You win");
    //                 return true;
    //             }
    //             if (random.nextDouble() >= heroDodge) {
    //                 int villainDamage = villain.getAttack() + random.nextInt(3);
    //                 hero.takeDamage(villainDamage);
    //                 System.out.println("HERO HP: " + hero.getHitPoints());
    //             } else 
    //                 System.out.println("Hero dodged the attack!");
    //             if (!hero.isAlive())
    //             {
    //                 consoleView.gameOver();
    //                 return false;
    //             }
    //         }
    //     }
    //     else
    //         runAway();
    //     return false;
	// }
		
		public void runAway() {
			Random random = new Random();
			int size = mapController.getSize();
			int newX, newY;
			do { 
				newX = random.nextInt(size);
				newY = random.nextInt(size);
			} while (mapController.getCell(newX, newY) != 0);
			mapController.setCell(x, y, 0);
			mapController.setCell(newX, newY, 2);
            x = newX;
            y = newY;
			consoleView.runMsg();	
    }

    public void updateHeroPosition(int newX, int newY) {
        x = newX;
        y = newY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Hero getHero() {
        return hero;
    }
}