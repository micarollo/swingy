package com.swingy;

import com.swingy.model.Hero;
import com.swingy.model.HeroCreator;

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
            Hero hero = HeroCreator.createHero();
            System.out.println("You have chosen: " + hero.getName());
            hero.displayStats();
        }
        else
            System.out.println("Must be: console o gui");
    }
}