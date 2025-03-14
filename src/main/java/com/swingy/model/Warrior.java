package com.swingy.model;

public class Warrior extends Hero {
	public Warrior(String name) {
		super(name, 1, 10, 8, 40, 0, 0, 0);
	}

	public Warrior(String name, int level, int attack, int defense, int hitPoints, int experience, int x, int y) {
		super(name, level, attack, defense, hitPoints, experience, x, y);
	}

	@Override
	public String getHeroClass() {
		return "Warrior";
	}
}