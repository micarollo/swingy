package com.swingy;

import com.swingy.controller.HeroController;
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
            HeroController heroController = new HeroController(consoleView);
            Hero hero = heroController.HeroCreator();
            consoleView.displayHeroStats(hero);
            consoleView.displayMenu();
        }
        else
            System.out.println("Must be: console o gui");
    }
}