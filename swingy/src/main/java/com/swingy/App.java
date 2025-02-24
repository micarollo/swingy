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
            // Warrior warrior = new Warrior("Conan");
            // System.out.println("Hero created: " + warrior.getName());
            // System.out.println("Class: " + warrior.getHeroClass());
            // System.out.println("Level: " + warrior.getLevel());
            // System.out.println("Attack: " + warrior.getAttack());
            // System.out.println("Defense: " + warrior.getDefense());
            // System.out.println("Hit Points: " + warrior.getHitPoints());
            // System.out.println("-------------------------");

            // System.out.println("Conan gains 1500 XP...");
            // warrior.gainExperience(1500);

            // System.out.println("After gaining XP:");
            // System.out.println("Level: " + warrior.getLevel());
            // System.out.println("Attack: " + warrior.getAttack());
            // System.out.println("Defense: " + warrior.getDefense());
            // System.out.println("Hit Points: " + warrior.getHitPoints());
            Hero hero = HeroCreator.createHero();
            System.out.println("You have chosen: " + hero.getName());
            // System.out.println("Hero Stats - HP: " + hero.getHitPoints() + ", Attack: " + hero.getAttack() + ", Defense: " + hero.getDefense());
            hero.displayStats();
        }
        else
            System.out.println("Must be: console o gui");
    }
}