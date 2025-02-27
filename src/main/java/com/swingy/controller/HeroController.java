package com.swingy.controller;

import java.util.Random;

import com.swingy.model.Hero;
import com.swingy.model.Mage;
import com.swingy.model.Villain;
import com.swingy.model.Warrior;
import com.swingy.view.ConsoleView;

public class HeroController {
    private final ConsoleView consoleView;
    private final MapController mapController;
    private final VillainController villainController;
    private Hero hero;
    private int x;
    private int y;

    public HeroController(ConsoleView consoleView, MapController mapController, VillainController villainController) {
        this.consoleView = consoleView;
        this.mapController = mapController;
        this.villainController = villainController;
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
                int choose = consoleView.displayFightorRun();
                if (handleBattle(choose, (x + nx), (y + ny))) {
                    mapController.updateMap(x, y, 0);
                    x = x + nx;
                    y = y + ny;
                    mapController.updateMap(x, y, 2);
                }
                break;
            case -1:
                //displayOutOfMap();
                break;
        }
    }

    public boolean handleBattle(int choose, int villainX, int villainY) {
        if (choose == 1) {
            Villain villain = villainController.villainCreator(hero.getLevel());
            System.out.println("fight");
            while (hero.isAlive() && villain.isAlive()) {
                villain.takeDamage(hero.getAttack());
                System.out.println("VILLAIN HP: " + villain.getHitPoints());
                if (!villain.isAlive())
                {
                    System.out.println("You win");
                    // mapController.setCell(x, y, 0);
                    // mapController.setCell(villainX, villainY, 2);
                    return true;
                }
                hero.takeDamage(villain.getAttack());
                System.out.println("HERO HP: " + hero.getHitPoints());
                if (!hero.isAlive())
                {
                    System.out.println("The villain won");
                    return false;
                }
            }
        }
        else
            runAway();
        return false;
	}
		
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