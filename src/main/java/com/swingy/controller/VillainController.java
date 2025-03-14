package com.swingy.controller;

import java.util.Random;

import com.swingy.model.Villain;

public class VillainController {
	private Random random = new Random();

	public Villain villainCreator(int heroLevel) {
		String types[] = {"Demon", "Wolf", "Vampire"};
		String newVillain = types[random.nextInt(types.length)];
		int villainLevel = heroLevel + (random.nextInt(3) - 1);
		if (villainLevel < 1)
			villainLevel = 1;
		int attack = 10 + villainLevel * villainLevel;
		int defense = 5 + villainLevel * villainLevel / 2;
		int hitPoints = 20 + villainLevel * villainLevel * 3;
		return new Villain(newVillain, villainLevel, attack, defense, hitPoints);
	}
}
