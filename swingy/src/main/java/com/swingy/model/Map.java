package com.swingy.model;

import java.util.Random;

public class Map {
    private int[][] map;
    private int size;
    private int maxVillians;
    // private int heroX;
    // private int heroY;

    public Map(int level) {
        this.size = calculateMap(level);
        this.maxVillians = calculateVillains();
        this.map = new int[size][size];
        generateMap();
        placeHero();
    }

    public int calculateMap(int level) {
        return (level - 1) * 5 + 10 - (level % 2);
    }

    public int calculateVillains() {
        return (int) (size * size * 0.1);
    }

    public void generateMap() {
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                map[i][j] = 0;
            }
        }
        int placedVillains = 0;
        while (placedVillains < maxVillians) {
            int x = random.nextInt(size);
            int y = random.nextInt(size);

            if (map[x][y] == 0) {
                map[x][y] = 1;
                placedVillains++;
            }
        }
    }

    private void placeHero() {
        x = size / 2;
        y = size / 2;
        map[x][y] = 2;
    }

    public int getCell(int x, int y) {
        if (x >= 0 && x < size && y >= 0 && y < size)
            return map[x][y];
        return -1;
    }

    public int getSize() {
        return size;
    }

    // public int getHeroX() {
    //     return heroX;
    // }

    // public int getHeroY() {
    //     return heroY;
    // }
}