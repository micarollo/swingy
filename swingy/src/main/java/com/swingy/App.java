package com.swingy;

import com.swingy.controller.HeroController;
import com.swingy.controller.MapController;
import com.swingy.model.Hero;
import com.swingy.view.ConsoleView;

public class App 
{
    public static void main( String[] args )
    {
        if (args.length != 1)
        {
            System.out.println("Usage: java -jar swingy.jar <console/gui>");
            return;
        }
        if (args[0].equals("console")) 
        {
            ConsoleView consoleView = new ConsoleView();
            MapController mapController = new MapController();
            mapController.createMap(1);
            int size = mapController.getSize();
            HeroController heroController = new HeroController(consoleView, mapController, size / 2, size / 2);
            Hero hero = heroController.HeroCreator();
            consoleView.displayHeroStats(hero);
            // consoleView.displayMenu();
            // Map map = new Map(hero.getLevel());
            consoleView.displayMap(mapController.getMap());
        }
        else
            System.out.println("Must be: console o gui");
    }
}