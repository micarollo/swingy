package com.swingy.model;

import java.util.Scanner;

public class HeroCreator {
    public static Hero createHero() {
        Scanner scanner = new Scanner(System.in);
        Hero hero;
        
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

        System.out.print("Enter your hero's name: ");
        String heroName = scanner.nextLine();
        switch (choice) {
            case 1:
                hero = new Warrior(heroName);
                break;
            case 2:
                hero = new Mage(heroName);
                break;
            default:
                System.out.println("Invalid choice. Defaulting to Warrior.");
                hero = new Warrior("Default Warrior");
        }
        return hero;
    }
}
