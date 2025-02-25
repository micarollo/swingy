package com.swingy.view;

import java.util.Scanner;

import com.swingy.model.Hero;

public class HeroView {
    Scanner scanner = new Scanner(System.in);

    public int setHeroClass() {
        System.out.println("Choose your Hero class:");
        System.out.println("1. Warrior");
        System.out.println("2. Mage");

        int choice = 0;
        boolean valid = false;
        while (!valid) {
            System.out.print("Enter the number of the class you want: ");
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
                if (choice == 1 || choice == 2) {
                    valid = true;
                } else {
                    System.out.println("Invalid choice. Please enter 1 or 2.");
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine();
            }
        }
        return choice;
    }

    public String setHeroName() {
        System.out.print("Enter your hero's name: ");
        return scanner.nextLine();
    }

    public void displayHeroCreation(String heroClass, String heroName) {
        System.out.println("Successfully created a " + heroClass + ": " + heroName);
    }
    
    public void displayStats(Hero hero) {
        System.out.println(hero.getHeroClass() + " Stats for " + hero.getName() + ":");
        System.out.println("Level: " + hero.getLevel());
        System.out.println("HitPoints: " + hero.getHitPoints());
        System.out.println("Attack: " + hero.getAttack());
        System.out.println("Defense: " + hero.getDefense());
        System.out.println("Experience: " + hero.getExperience());
    }

    public void displayExperienceGain(Hero hero, int xp) {
        System.out.println(hero.getName() + " won " + xp + " XP. Total: " + hero.getExperience());
    }
}