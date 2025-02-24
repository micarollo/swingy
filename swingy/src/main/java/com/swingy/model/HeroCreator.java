package com.swingy.model;

import java.util.Scanner;

public class HeroCreator {
    public static Hero createHero() {
        Scanner scanner = new Scanner(System.in);

        // Mostrar opciones al usuario
        System.out.println("Choose your Hero class:");
        System.out.println("1. Warrior");
        System.out.println("2. Mage");
        System.out.print("Enter the number of the class you want: ");

        // Leer la elección del usuario
        int choice = scanner.nextInt();

        Hero hero;
        switch (choice) {
            case 1:
                hero = new Warrior("Warrior");
                break;
            case 2:
                hero = new Mage("Mage");
                break;
            default:
                System.out.println("Invalid choice. Defaulting to Warrior.");
                hero = new Warrior("Default Warrior");
        }

        return hero;
    }
}
