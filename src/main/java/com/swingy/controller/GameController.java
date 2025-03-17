package com.swingy.controller;

import java.util.Random;
import java.util.Scanner;

import com.swingy.DbManager;
import com.swingy.model.Armor;
import com.swingy.model.Artifact;
import com.swingy.model.ArtifactGenerator;
import com.swingy.model.Helm;
import com.swingy.model.Hero;
import com.swingy.model.Villain;
import com.swingy.model.Weapon;
import com.swingy.view.ConsoleView;

public class GameController {
	private final DbManager dbManager;
	private final MapController mapController;
	private final ConsoleView consoleView;
	private final HeroController heroController;
	private final VillainController villainController;
	private Hero hero;

	public GameController() {
		this.dbManager = new DbManager("jdbc:sqlite:swingy.db");
		this.consoleView = new ConsoleView();
		this.mapController = new MapController();
		mapController.createMap(1);
		this.villainController = new VillainController();
		this.heroController = new HeroController(consoleView, mapController, this, dbManager);
	}

	public void startGame() {
		dbManager.createHeroesTable();
		Scanner scan = new Scanner(System.in);
		int choice = consoleView.displayWelcomeMessage();
		if (choice == 1) {
			dbManager.displayHeroes();
			int id = scan.nextInt();
			if (id == 0)
				startGame();
			hero = dbManager.getHeroById(id);
			heroController.setHero(hero);
			//check
			setArtifactsFromDB(hero);
			int villains = dbManager.getVillains(id);
			System.out.println(hero);
			mapController.createMap(hero.getLevel());
			mapController.setUpMap(hero.getX(), hero.getY(), villains);
		}
		else {
			hero = heroController.HeroCreator();
			dbManager.saveHero(hero);
			mapController.setUpMap(mapController.getMap().getSize() / 2, mapController.getMap().getSize() / 2, mapController.getMap().calculateVillains());
			dbManager.updateVillains(mapController.getMap().getMaxVillains(), hero.getName());
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
		while (hero.isAlive() && villain.isAlive()) {
			try {
				if (random.nextDouble() >= villainDodge) {
					int heroDamage = hero.getBoostAttack() + random.nextInt(5);
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
					battleWon(villain, newX, newY);
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
					battleLost();
					Thread.sleep(1000);

				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}   
	}

	private void battleWon(Villain villain, int newX, int newY) {
		consoleView.winningMsg();
		mapController.getMap().killVillain();
		dbManager.updateVillains(mapController.getMap().getMaxVillains(), hero.getName());
		handleDropArtifact(villain.getLevel());
		mapController.setCell(hero.getX(), hero.getY(), 0);
		mapController.setCell(newX, newY, 2);
		heroController.updateHeroPosition(newX, newY);
		gainHeroExperience(hero, villain);
		dbManager.updateHero(hero);
	}

	private void battleLost() {
		dbManager.deleteHero(hero.getName());
		consoleView.gameOver();
	}

	public void handleDropArtifact(int villainLevel) {
		ArtifactGenerator artifactGenerator = new ArtifactGenerator();
		Random random = new Random();
		if (random.nextDouble() < 0.4) {
			Artifact artifact = artifactGenerator.generateArtifact(villainLevel);
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
		hero.gainExperience(xpGained);
		consoleView.displayExperienceGain(hero, xpGained);
		if (hero.needToLevelUp()) {
			hero.levelUp(hero.calculateLevelUp(hero.getLevel()));
			mapController.changeLevel(hero.getLevel());
			heroController.updateHeroPosition((mapController.getSize() / 2), (mapController.getSize() / 2));
			System.out.println("X: " + hero.getX() + " Y: " + hero.getY());
			System.out.println("HP: " + hero.getHitPoints());
		}
	}

	private void setArtifactsFromDB(Hero hero) {
		int[] artifacts = dbManager.getHeroBoosts(hero.getName());
		int weaponBoost = artifacts[0];
		int armorBoost = artifacts[1];
		int helmBoost = artifacts[2];

		if (weaponBoost != 0) {
			Weapon weapon = new Weapon(weaponBoost);
			hero.setWeapon(weapon);
		}

		if (armorBoost != 0) {
			Armor armor = new Armor(armorBoost);
			hero.setArmor(armor);
		}

		if (helmBoost != 0) {
			Helm helm = new Helm(helmBoost);
			hero.setHelm(helm);
		}
	}

	public void exitGame() {
		System.exit(0);
	}
}