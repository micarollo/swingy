package com.swingy.model;

public class Map {
	private final int[][] gridMap;
	private final int size;
	private int maxVillians;

	public Map(int level) {
		this.size = calculateMap(level);
		this.maxVillians = calculateVillains();
		this.gridMap = new int[size][size];
	}

	public int calculateMap(int level) {
		return (level - 1) * 5 + 10 - (level % 2);
	}

	public int calculateVillains() {
		return (int) (size * size * 0.3);
	}

	public int getCell(int x, int y) {
		if (x >= 0 && x < size && y >= 0 && y < size)
			return gridMap[x][y];
		return -1;
	}

	public void setCell(int x, int y, int value) {
		if (x >= 0 && x < size && y >= 0 && y < size)
			gridMap[x][y] = value;
	}

	public int getSize() {
		return size;
	}

	public int getMaxVillains() {
		return maxVillians;
	}

	public void killVillain() {
		this.maxVillians--;
	}
}