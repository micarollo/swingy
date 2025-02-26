package com.swingy.controller;

import com.swingy.view.ConsoleView;
import com.swingy.model.Hero;

public class GameController {
    private final MapController mapController;
    private final ConsoleView consoleView;
    private final HeroController heroController;

    public GameController() {
        this.consoleView = new ConsoleView();
        this.mapController = new MapController();
        this.heroController = new HeroController(consoleView, mapController);
    }

    public void startGame() {
        mapController.createMap(1);
        int size = mapController.getSize();
        Hero hero = heroController.HeroCreator();
        consoleView.displayHeroStats(hero);
        consoleView.displayMap(mapController.getMap());

    }

    public void gameLoop() {
        while (true) { 
            int input = consoleView.displayMenu();
            handleInput(input);
        }
    }

    public void handleInput(int input) {
        switch (input) {
            case 1:
                heroController.moveHero(0, -1);
                break;
            case 2:
                heroController.moveHero(0, 1);
                break;
            case 3:
                heroController.moveHero(1, 0);
                break;
            case 4:
                heroController.moveHero(-1, 0);
                break;
            default:
                throw new AssertionError();
        }
    }
}