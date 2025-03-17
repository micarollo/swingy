package com.swingy.controller;

import java.util.Random;

import com.swingy.model.Map;

public class MapController {
	private Map map;

	public MapController() {}

	public void createMap(int level) {
		this.map = new Map(level);
	}

	public void setUpMap(int x, int y, int v) {
		fillMap();
		placeHero(x, y);
		map.setMaxVillains(v);
		placeVillains();
	}

	public void fillMap() {
		int size = map.getSize();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				map.setCell(i, j, 0);
			}
		}
	}

	public void placeHero(int heroX, int heroY) {
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

	public void changeLevel(int level) {
		createMap(level);
		setUpMap(map.getSize() / 2, map.getSize() / 2, map.calculateVillains());
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