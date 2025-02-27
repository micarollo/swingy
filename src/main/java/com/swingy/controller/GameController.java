package com.swingy.controller;

import com.swingy.model.Hero;
import com.swingy.view.ConsoleView;

public class GameController {
    private final MapController mapController;
    private final ConsoleView consoleView;
    private final HeroController heroController;
    private final VillainController villainController;
    private Hero hero;

    public GameController() {
        this.consoleView = new ConsoleView();
        this.mapController = new MapController();
        mapController.createMap(1);
        this.villainController = new VillainController();
        this.heroController = new HeroController(consoleView, mapController, villainController);
    }

    public void startGame() {
        // int size = mapController.getSize();
        hero = heroController.HeroCreator();
        consoleView.displayHeroStats(hero);
        consoleView.displayMap(mapController.getMap());
        gameLoop();
    }

    public void gameLoop() {
        while (true) { 
            int input = consoleView.displayMenu();
            handleInput(input);
            consoleView.displayMap(mapController.getMap());
        }
    }

    public void handleInput(int input) {
        switch (input) {
            case 1:
                heroController.moveHero(-1, 0);
                break;
            case 2:
                heroController.moveHero(1, 0);
                break;
            case 3:
                heroController.moveHero(0, 1);
                break;
            case 4:
                heroController.moveHero(0, -1);
                break;
            case 5:
                consoleView.displayHeroStats(hero);
                break;
            case 6:
                consoleView.displayExitMessage();
                exitGame();
                break;
            default:
                throw new AssertionError();
        }
    }

    public void exitGame() {
        System.exit(0);
    }
}