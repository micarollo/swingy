package com.swingy.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public abstract class Character {
	@NotNull(message = "Name must not be null.")
	// @Size(min=3, max=10, message = "Name size is not acceptable.")
	protected String name;
	@Min(value = 1, message = "Level must be at least 1")
	protected int level;
	@Min(value = 0, message = "Attack cannot be negative")
	protected int attack;
	@Min(value = 0, message = "Defense cannot be negative")
	protected int defense;
	@Min(value = 0, message = "Hit Points cannot be negative")
	protected int hitPoints;

	public Character(String name, int level, int attack, int defense, int hitPoints) {
		this.name = name;
		this.level = level;
		this.attack = attack;
		this.defense = defense;
		this.hitPoints = hitPoints;
	}

	public void takeDamage(int damage) {
		hitPoints -= (damage - defense);
	}

	public boolean isAlive() {
		return hitPoints > 0;
	}

	public String getName() { return name; }
	public int getLevel() { return level; }
	public int getAttack() { return attack; }
	public int getDefense() { return defense; }
	public int getHitPoints() { return hitPoints; }
}