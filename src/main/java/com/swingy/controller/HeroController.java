package com.swingy.controller;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import com.swingy.DbManager;
import com.swingy.model.Hero;
import com.swingy.model.Mage;
import com.swingy.model.Warrior;
import com.swingy.view.ConsoleView;
import com.swingy.view.GuiView;

public class HeroController {
	private final ConsoleView consoleView;
	private final GuiView guiView;
	private final MapController mapController;
	private final GameController gameController;
	private final DbManager dbManager;
	private Hero hero;
	Random random = new Random();

	public HeroController(ConsoleView consoleView, GuiView guiView, MapController mapController, GameController gameController, DbManager dbManager) {
		this.consoleView = consoleView;
		this.guiView = guiView;
		this.mapController = mapController;
		this.gameController = gameController;
		this.dbManager = dbManager;
	}

	public Hero HeroCreator(String cl, String name) {
		switch (cl.toLowerCase()) {
			case "warrior":
				hero = new Warrior(name);
				consoleView.displayHeroCreation("Warrior", name);
				break;
			case "mage":
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
		return hero;
	}

	public void moveHero(int nx, int ny, boolean guiMode) {
		int result = mapController.isValidMove((hero.getX() + nx), (hero.getY() + ny));
		switch (result) {
			case 0:
				updatePosition(nx, ny);
				if (guiMode)
					guiView.repaintMap(mapController.getMap().getGridMap());
				handleHpRecovery(guiMode);
				// mapController.updateMap(hero.getX(), hero.getY(), 0);
				// updateHeroPosition((hero.getX() + nx), (hero.getY() + ny));
				// mapController.updateMap(hero.getX(), hero.getY(), 2);
				// dbManager.updateHeroPos(hero.getX(), hero.getY(), hero.getName());
				//IDEA to recover hp
				// int foundHP = ThreadLocalRandom.current().nextInt(10);
            	// if (foundHP < 5) { // probabilidad
                // 	int hpRecovered = ThreadLocalRandom.current().nextInt(1, 5); // cant de hp
                // 	System.out.println("HitPoints: " + hero.getHitPoints());
				// 	hero.recoverHp(hpRecovered);
				// 	dbManager.updateHero(hero);
                // 	System.out.println("\uD83E\uDDEA You found a health potion! Restored " + hpRecovered + " HP.");
				// 	System.out.println("HitPoints: " + hero.getHitPoints());
				// }
				break;
			case 1:
				handleEnemyEncounter((hero.getX() + nx), (hero.getY() + ny), guiMode);
				// int choose = consoleView.displayFightorRun();
				// if (choose == 1)
				// {
				// 	gameController.handleBattle(hero, (hero.getX() + nx), (hero.getY() + ny));
				// } else {
				// 	int luck = ThreadLocalRandom.current().nextInt(10);
				// 	if (luck < 7) {
				// 		runAway();
				// 	} else {
				// 		System.out.println("Bad luck, the villain don't let you run and you have to fight!!");
				// 		gameController.handleBattle(hero, (hero.getX() + nx), (hero.getY() + ny));
				// 	}
				// }
				break;
			case -1:
				//displayOutOfMap();
				break;
		}
	}

	private void updatePosition(int nx, int ny) {
		mapController.updateMap(hero.getX(), hero.getY(), 0);
		updateHeroPosition((hero.getX() + nx), (hero.getY() + ny));
		mapController.updateMap(hero.getX(), hero.getY(), 2);
		dbManager.updateHeroPos(hero.getX(), hero.getY(), hero.getName());
	}

	private void handleHpRecovery(boolean guiMode) {
		if (ThreadLocalRandom.current().nextInt(10) < 5) {
			int hpRecovered = ThreadLocalRandom.current().nextInt(1, 5); // cant de hp
			hero.recoverHp(hpRecovered);
			dbManager.updateHero(hero);
			if (guiMode) {
				guiView.showHpRecoveryMessage(hpRecovered, hero.getHitPoints());
			} else {
				System.out.println("\uD83E\uDDEA You found a health potion! Restored " + hpRecovered + " HP.");
				System.out.println("HitPoints: " + hero.getHitPoints());
			}
		}
	}

	private void handleEnemyEncounter(int enemyX, int enemyY, boolean guiMode) {
		if (guiMode) {
			// int option = guiView.showFightOrRunDialog();
			// handleBattleDecision(option, enemyX, enemyY, guiMode);
		} else {
			int option = consoleView.displayFightorRun();
			handleBattleDecision(option, enemyX, enemyY, guiMode);
		}
	}

	private void handleBattleDecision(int option, int enemyX, int enemyY, boolean guiMode) {
		if (option == 1) {
			gameController.handleBattle(hero, enemyX, enemyY);
		} else { // Huir
			if (ThreadLocalRandom.current().nextInt(10) < 7) {
				runAway();
				if (guiMode) 
					System.out.println("run from GUImode");
				else
					consoleView.runMsg();	
			} else {
				// if (guiMode) 
					// guiView.showMessage("¡Mala suerte! El enemigo te atrapó.");
				// else 
					System.out.println("Bad luck, the villain don't let you run and you have to fight!!");
				gameController.handleBattle(hero, enemyX, enemyY);
			}
		}
	}

	public void runAway() {
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
		// consoleView.runMsg();	
	}

	public void updateHeroPosition(int newX, int newY) {
		hero.setX(newX);
		hero.setY(newY);
	}

	public Hero getHero() {
		return hero;
	}

	public void setHero(Hero hero) {
		this.hero = hero;
	}
}