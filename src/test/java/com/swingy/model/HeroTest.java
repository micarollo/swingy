package com.swingy.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HeroTest{
	private Hero hero;

	@BeforeEach
	public void setUp() {
		hero = new Warrior("TestHero", 1, 10, 5, 100, 0, 5, 5);
	}

	@Test
	public void testGainExperience() {
		hero.gainExperience(500);
		assertEquals(500, hero.getExperience());
	}

	@Test
	public void testCalculateLevelUp() {
		int requiredXP = hero.calculateLevelUp(5);
		assertEquals(12200, requiredXP);
	}

	@Test
	public void testNeedToLevelUp_False() {
		assertFalse(hero.needToLevelUp());
	}

	@Test
	public void testNeedToLevelUp_True() {
		hero.gainExperience(1500);
		assertTrue(hero.needToLevelUp());
	}

	@Test
	public void testLevelUp() {
		hero.gainExperience(2600);
		hero.levelUp(2450);
		assertEquals(150, hero.getExperience());
	}

	@Test
	public void testEquipWeapon() {
		Weapon weapon = new Weapon(5);
		hero.equipArtifact(weapon);
		assertEquals(weapon, hero.getWeapon());
		assertEquals(5, weapon.getBoost());
		assertEquals(15, hero.getBoostAttack());
		assertEquals(10, hero.getAttack());
	}

	@Test
	public void testSetAndGetPosition() {
		hero.setX(10);
		hero.setY(15);
		assertEquals(10, hero.getX());
		assertEquals(15, hero.getY());
	}
}