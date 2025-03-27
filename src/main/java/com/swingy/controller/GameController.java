package com.swingy.controller;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.swing.SwingUtilities;

import com.swingy.DbManager;
import com.swingy.model.Armor;
import com.swingy.model.Artifact;
import com.swingy.model.ArtifactGenerator;
import com.swingy.model.Helm;
import com.swingy.model.Hero;
import com.swingy.model.Villain;
import com.swingy.model.Weapon;
import com.swingy.view.ConsoleView;
import com.swingy.view.GuiView;

public class GameController {
	private final DbManager dbManager;
	private final MapController mapController;
	private final ConsoleView consoleView;
	private final GuiView guiView;
	private final HeroController heroController;
	private final VillainController villainController;
	private Hero hero;
	private boolean guiMode = false;

	public GameController() {
		this.dbManager = new DbManager("jdbc:sqlite:swingy.db");
		this.consoleView = new ConsoleView();
		this.guiView = new GuiView(this);
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
			String cl = consoleView.chooseHeroClass();
			String name = consoleView.chooseHeroName();
			hero = heroController.HeroCreator(cl, name);
			dbManager.saveHero(hero);
			mapController.setUpMap(mapController.getMap().getSize() / 2, mapController.getMap().getSize() / 2, mapController.getMap().calculateVillains());
			dbManager.updateVillains(mapController.getMap().getMaxVillains(), hero.getName());
		}
		consoleView.displayHeroStats(hero);
		consoleView.displayMap(mapController.getMap());
		gameLoop();
	}

	public void startGuiModeGame() {
		SwingUtilities.invokeLater(() -> {
			guiMode = true;
            guiView.init();
			guiView.setVisible(true);
        });
	}

	public void selectHeroGuiMode() {
		List<Object[]> heroesData = dbManager.getAllHeroData();
		int id = guiView.showHeroSelectionDialog(heroesData);
		System.out.println(id);
		//todo: create new method to reuse this code
		hero = dbManager.getHeroById(id);
		heroController.setHero(hero);
		setArtifactsFromDB(hero);
		int villains = dbManager.getVillains(id);
		System.out.println(hero);
		mapController.createMap(hero.getLevel());
		mapController.setUpMap(hero.getX(), hero.getY(), villains);
		guiView.drawMap(mapController.getMap().getGridMap());
	}

	public void createNewHeroGuiMode() {
		String[] heroData;
		heroData = guiView.showNewHeroDialog();
		System.out.println(heroData[0] + ": " + heroData[1]);
		hero = heroController.HeroCreator(heroData[0], heroData[1]);
		dbManager.saveHero(hero);
		mapController.setUpMap(mapController.getMap().getSize() / 2, mapController.getMap().getSize() / 2, mapController.getMap().calculateVillains());
		dbManager.updateVillains(mapController.getMap().getMaxVillains(), hero.getName());
		guiView.drawMap(mapController.getMap().getGridMap());
	}

	public void gameLoop() {
		while (true) {
			char input = consoleView.displayMenu();
			if (input != '\0') {
				handleInput(input);
				consoleView.displayMap(mapController.getMap());
			}
		}
	}

	public void handleInput(char choice) {
		switch (choice) {
			case 'w': heroController.moveHero(-1, 0); break;
			case 's': heroController.moveHero(1, 0); break;
			case 'd': heroController.moveHero(0, 1); break;
			case 'a': heroController.moveHero(0, -1); break;
			case 'e': consoleView.displayHeroStats(hero); break;
			case 'q': consoleView.displayExitMessage(); exitGame(); break;
			default:
				throw new AssertionError();
				// System.out.println("invalid option");
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
					consoleView.heroAttackMsg(villain);
					Thread.sleep(500);
				} else {
					consoleView.dodgedMsg("Villain");
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
					consoleView.villainAttackMsg(hero);
					Thread.sleep(500);
				} else {
					consoleView.dodgedMsg("Hero");
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
			consoleView.badLuckMsg();
			// System.out.println("bad luck: the villain didnt drop any artifact!!");
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
			// System.out.println("X: " + hero.getX() + " Y: " + hero.getY());
			// System.out.println("HP: " + hero.getHitPoints());
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