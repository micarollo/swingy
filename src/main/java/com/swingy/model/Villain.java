package com.swingy.model;

public class Villain extends Character {
	private String villainClass;

	public Villain(String name, int level, int attack, int defense, int hitPoints) {
		super(name, level, attack, defense, hitPoints);
	}

	public String getVillainClass() {
		return villainClass;
	}

	public int getPower() {
		int power = (getAttack() + getDefense() + getHitPoints() + getLevel()) * 2;
		return power;
	}

}