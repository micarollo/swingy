package com.swingy.controller;

import java.util.Random;

import com.swingy.model.Map;

public class MapController {
	private Map map;

	public MapController() {}

	public void createMap(int level) {
		this.map = new Map(level);
		fillGridMap();
	}
// idea: fill grid map solo 0
//fill villains
//place hero o algo asi
	public void fillMap() {
		int size = map.getSize();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				map.setCell(i, j, 0);
			}
		}
	}

	public void placeHero2(int heroX, int heroY) {
		map.setCell(heroX, heroY, 2);
	}

	public void placeVillains() {
		int size = map.getSize();
		Random random = new Random();
		int maxVillians = map.getMaxVillains();
		int placedVillains = 0;
		while (placedVillains < maxVillians) {
			int x = random.nextInt(size);
			int y = random.nextInt(size);

			if (map.getCell(x, y) == 0) {
				map.setCell(x, y, 1);
				placedVillains++;
			}
		}
	}

	public void fillGridMap() {
		Random random = new Random();
		int size = map.getSize();
		int maxVillians = map.getMaxVillains();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				map.setCell(i, j, 0);
			}
		}
		placeHero();
		int placedVillains = 0;
		while (placedVillains < maxVillians) {
			int x = random.nextInt(size);
			int y = random.nextInt(size);

			if (map.getCell(x, y) == 0) {
				map.setCell(x, y, 1);
				placedVillains++;
			}
		}
	}

	private void placeHero() {
		int size = map.getSize();
		int x = size / 2;
		int y = size / 2;
		map.setCell(x, y, 2);
	}

	public void changeLevel(int level) {
		createMap(level);
	}

//chage this!!
	public void createMapfromDb(int level, int heroX, int heroY, int villains) {
		this.map = new Map(level);
		Random random = new Random();
		int size = map.getSize();
		if (villains == 0)
			villains = map.getMaxVillains();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				map.setCell(i, j, 0);
			}
		}
		map.setCell(heroX, heroY, 2);
		int placedVillains = 0;
		while (placedVillains < villains) {
			int x = random.nextInt(size);
			int y = random.nextInt(size);

			if (map.getCell(x, y) == 0) {
				map.setCell(x, y, 1);
				placedVillains++;
			}
		}
		map.setMaxVillains(villains);
	}

	public int isValidMove(int nextX, int nextY) {
		int size = map.getSize();
		if (nextX < 0 || nextX >= size || nextY < 0 || nextY >= size) {
			return -1; // Movimiento fuera de los límites
		}
		if (map.getCell(nextX, nextY) == 1) {
			return 1; // villano
		}
		else
			return 0;
	}

	public void setCell(int x, int y, int value) {
		map.setCell(x, y, value);
	}

	public int getCell(int x, int y) {
		int result = map.getCell(x, y);
		return result;
	}

	public int getSize() {
		return map.getSize();
	}

	public Map getMap() {
		return map;
	}

	//modificar: coordenadas antiguas y nuevas, valor ant valor nuevo
	public void updateMap(int x, int y, int value) {
		map.setCell(x, y, value);
	}
}