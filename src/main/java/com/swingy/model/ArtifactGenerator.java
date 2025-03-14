package com.swingy.model;

import java.util.Random;

public class ArtifactGenerator {
	private Random random = new Random();

	public Artifact generateArtifact(int villainLevel) {
		int boost = random.nextInt(5 * villainLevel) + 1;
		int type = random.nextInt(3);

		switch (type) {
			case 0:
				return new Weapon(boost);
			case 1:
				return new Armor(boost);
			case 2:
				return new Helm(boost);
			default:
				return null;
		}
	}
}