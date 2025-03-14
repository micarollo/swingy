package com.swingy.model;

import java.util.Random;

public abstract class Hero extends Character {
	private int experience;
	private final int startingHP;
	private int x;
	private int y;
	private Helm helm;
	private Armor armor;
	private Weapon weapon;

	public Hero(String name, int level, int attack, int defense, int hitPoints, int experience, int x, int y) {
		super(name, level, attack, defense, hitPoints);
		this.experience = experience;
		this.startingHP = hitPoints;
		this.x = x;
		this.y = y;
	}

	public void gainExperience(int xp) {
		experience += xp;
	}

	public int calculateLevelUp(int level) {
		return (level * 1000) + ((level - 1) * (level - 1) * 450);
	}

	public boolean needToLevelUp() {
		int requiredXP = calculateLevelUp(getLevel());
		return getExperience() >= requiredXP;
	}
	//maybe to heroController
	public void levelUp(int requiredXP) {
		level++;
		experience -= requiredXP;
		attack += new Random().nextInt(3) + 1;
		defense += new Random().nextInt(3) + 1;
		hitPoints = getStartingHP();
		hitPoints += new Random().nextInt(5) + 3;
	}

	public void setX(int value) {
		x = value;
	}

	public void setY(int value) {
		y = value;
	}

	public void equipArtifact(Artifact artifact) {
		if (artifact instanceof Weapon newWeapon) {
			setWeapon(newWeapon);
		} else if (artifact instanceof Armor newArmor) {
			setArmor(newArmor);
		} else if (artifact instanceof Helm newHelm) {
			setHelm(newHelm);
		}
	}

	public int getBoostAttack() {
		if (this.weapon != null)
			return attack + weapon.getBoost();
		return attack;
	}

	public int getBoostDefense() {
		if (this.armor != null)
			return defense + armor.getBoost();
		return defense;
	}

	public int getBoostHitPoints() {
		if (this.helm != null)
			return hitPoints + helm.getBoost();
		return hitPoints;
	}

	public void setLevel(int level) { this.level = level; }
	public void setWeapon(Weapon weapon) { this.weapon = weapon; }
	public void setArmor(Armor armor) { this.armor = armor; }
	public void setHelm(Helm helm) { this.helm = helm; }
	public Armor getArmor() { return armor; }
	public Helm getHelm() { return helm; }
	public Weapon getWeapon() { return weapon; }
	public int getExperience() { return experience; }
	public int getStartingHP() { return startingHP; }
	public int getX() { return x; }
	public int getY() { return y; }
	public abstract String getHeroClass();
}

